package org.pinggu.portforu.domain.subscribe.scheduler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pinggu.portforu.common.lock.RedisLockExecutor;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.membership.entity.Membership;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.pinggu.portforu.domain.subscribe.enums.SubscribeStatus;
import org.pinggu.portforu.domain.subscribe.repository.SubscribeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscribeSchedulerTest {

    @InjectMocks
    private CanceledSubscribeScheduler canceledSubscribeScheduler;

    @InjectMocks
    private SubscribeScheduler subscribeScheduler;

    @Mock
    private SubscribeRepository subscribeRepository;

    @Mock
    private RedisLockExecutor redisLockExecutor;

    @Mock
    private JdbcTemplate jdbcTemplate;

    private Membership membership;
    private Subscribe subscribe;
    private Member member;

    @BeforeEach
    void setup() {
        membership = new Membership("test", 1000, 5, 2025);

        member = Member.builder()
                .email("test@email.com")
                .name("testname")
                .build();
        ReflectionTestUtils.setField(member, "id", 1L);

        Instant now = Instant.now();
        Instant end = now.plusSeconds(3600); // 1시간 후 만료

        subscribe = Subscribe.builder()
                .member(member)
                .membership(membership)
                .startDate(now)
                .endDate(end)
                .build();

        ReflectionTestUtils.setField(subscribe, "id", 1L);

        doAnswer(invocation -> {
            Runnable runnable = invocation.getArgument(3);
            runnable.run();
            return null;
        }).when(redisLockExecutor).executeWithLock(anyString(), anyLong(), anyLong(), any(Runnable.class));
    }

    @Test
    void ACTIVE_구독_만료_정원증가_테스트() {
        // given
        when(subscribeRepository.findAllByStatusAndEndDateBefore(eq(SubscribeStatus.ACTIVE), any(Instant.class)))
                .thenReturn(List.of(subscribe));

        // when
        subscribeScheduler.expireEndedSubscriptions();

        // then
        verify(jdbcTemplate).update(anyString(), anyLong());
    }

    @Test
    void CANCELED_구독_만료_정원증가_테스트() {
        // given
        when(subscribeRepository.findAllByStatusAndEndDateBefore(eq(SubscribeStatus.CANCELED), any(Instant.class)))
                .thenReturn(List.of(subscribe));

        // when
        canceledSubscribeScheduler.expireCanceledSubscriptions();

        // then
        verify(jdbcTemplate).update(anyString(), anyLong());
    }
}
