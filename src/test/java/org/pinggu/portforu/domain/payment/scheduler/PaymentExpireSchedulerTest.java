package org.pinggu.portforu.domain.payment.scheduler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pinggu.portforu.domain.payment.service.PaymentExpireService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentExpireSchedulerTest {

    @Mock
    RedisTemplate<Object, Object> redisTemplate;
    @Mock
    ValueOperations<Object, Object> ops;
    @Mock
    PaymentExpireService expireService;
    @Mock
    ScheduledExecutorService executorService;

    @InjectMocks
    PaymentExpireScheduler scheduler;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(ops);
    }

    @Test
    void 만료예약_스케줄러가Redis키에TTL을설정하고_스케줄예약한다() {
        // given
        Long subscribeId = 5L;
        Duration delay = Duration.ofMinutes(20);
        String expectedKey = "payment:expire:" + subscribeId;

        // when
        scheduler.scheduleExpire(subscribeId, delay);

        // then
        // 1) Redis에 키+TTL 설정 검증
        verify(ops).set(expectedKey, "1", delay);

        // 2) executorService.schedule(...) 호출 검증
        verify(executorService).schedule(
                any(Runnable.class),
                eq(delay.toMinutes()),
                eq(TimeUnit.MINUTES)
        );

        verifyNoMoreInteractions(expireService);
    }
}
