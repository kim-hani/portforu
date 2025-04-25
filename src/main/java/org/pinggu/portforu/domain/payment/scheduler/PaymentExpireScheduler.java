package org.pinggu.portforu.domain.payment.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.domain.payment.service.PaymentExpireService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentExpireScheduler {

    private final PaymentExpireService paymentExpireService;
    private final ScheduledExecutorService executorService;
    private final RedisTemplate<Object, Object> redisTemplate;

    public void scheduleExpire(Long subscribeId, Duration delay) {
        log.info("구독 ID {} 에 대해 {}분 후 결제 만료 예약", subscribeId, delay.toMinutes());
        String redisKey = "payment:expire:" + subscribeId;
        redisTemplate.opsForValue().set(redisKey, "1", delay);

        executorService.schedule(
                () -> {
                    try {
                        paymentExpireService.expireIfPending(subscribeId);
                    } catch (Exception e) {
                        log.error("결제 만료 처리 중 예외 발생: subscribeId={}", subscribeId, e);
                    }
                },
                delay.toMinutes(),
                TimeUnit.MINUTES
        );
    }
}
