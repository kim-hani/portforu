package org.pinggu.portforu.domain.payment.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.domain.payment.service.PaymentExpireService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentExpireScheduler {

    private final PaymentExpireService paymentExpireService;

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
    private final RedisTemplate<Object, Object> redisTemplate;

    public void scheduleExpire(Long subscribeId, Duration delay) {
        log.info("구독 ID {} 에 대해 {}분 후 결제 만료 예약", subscribeId, delay.toMinutes());
        // rediskey 생성
        String redisKey = "payment:expire:" + subscribeId;
        redisTemplate.opsForValue().set(redisKey, "1", delay); // 1번째는 키이름임, 2번재는 저장할 값인데 실제론 의미 없음 3번째 ttl 만료시간

        executorService.schedule(() -> {
            try {
                paymentExpireService.expireIfPending(subscribeId);
            } catch (Exception e) {
                log.error("결제 만료 처리 중 예외 발생: subscribeId={}", subscribeId, e);
            }
        }, delay.toMinutes(), TimeUnit.MINUTES);
    }
}