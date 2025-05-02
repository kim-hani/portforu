package org.pinggu.portforu.domain.payment.scheduler;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.domain.payment.enums.PaymentStatus;
import org.pinggu.portforu.domain.payment.repository.PaymentRepository;
import org.pinggu.portforu.domain.payment.service.PaymentExpireService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentExpireRescheduler {

    private final RedisTemplate<String, String> redisTemplate;
    private final PaymentExpireScheduler paymentExpireScheduler;
    private final PaymentExpireService paymentExpireService;
    private final PaymentRepository paymentRepository;

    private static final String KEY_PREFIX = "payment:expire:";

    @PostConstruct
    public void rescheduleTTLKeysAndRecoverExpiredOnes() {
        log.info("[복구] Redis TTL 재예약 + DB 만료 복구 시작");

        Set<String> keys = redisTemplate.keys(KEY_PREFIX + "*");

        if (keys != null) {
            for (String key : keys) {
                try {
                    String subscribeIdStr = key.replace(KEY_PREFIX, "");
                    Long subscribeId = Long.parseLong(subscribeIdStr);

                    Long ttlSeconds = redisTemplate.getExpire(key);
                    if (ttlSeconds != null && ttlSeconds > 0) {
                        log.info("재예약 대상: {} (남은 TTL: {}초)", subscribeId, ttlSeconds);
                        paymentExpireScheduler.scheduleExpire(subscribeId, Duration.ofSeconds(ttlSeconds));
                    }
                } catch (Exception e) {
                    log.warn("TTL 키 재예약 실패: key={}, 이유={}", key, e.getMessage());
                }
            }
        }

        // TTL이 만료되어 Redis에 키가 없는 애들 복구
        Instant twentyMinutesAgo = Instant.now().minus(Duration.ofMinutes(20));
        paymentRepository.findAllByStatusAndCreatedAtBefore(PaymentStatus.PENDING, twentyMinutesAgo)
                .forEach(payment -> {
                    log.info("TTL 없이 유실된 결제 복구 처리: subscribeId={}", payment.getSubscribe().getId());
                    paymentExpireService.expireIfPending(payment.getSubscribe().getId());
                });

        log.info("[복구] TTL 기반 예약 및 유실 결제 복구 완료");
    }
}
