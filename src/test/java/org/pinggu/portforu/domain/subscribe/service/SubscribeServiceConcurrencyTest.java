package org.pinggu.portforu.domain.subscribe.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.common.lock.RedisLockExecutor;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.pinggu.portforu.domain.membership.entity.Membership;
import org.pinggu.portforu.domain.membership.service.MembershipFinder;
import org.pinggu.portforu.domain.membership.repository.MembershipRepository;
import org.pinggu.portforu.domain.payment.entity.Payment;
import org.pinggu.portforu.domain.payment.enums.PaymentStatus;
import org.pinggu.portforu.domain.payment.repository.PaymentRepository;
import org.pinggu.portforu.domain.payment.scheduler.PaymentExpireScheduler;
import org.pinggu.portforu.domain.payment.service.PaymentFinder;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.pinggu.portforu.domain.subscribe.repository.SubscribeRepository;
import org.pinggu.portforu.domain.subscribe.dto.response.SubscribeResponseDto;
import org.pinggu.portforu.domain.subscribe.service.SubscribeFinder;
import org.pinggu.portforu.domain.subscribe.service.SubscribeService;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Year;
import java.time.Duration;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class SubscribeServiceConcurrencyTest {

    @Mock
    RedisLockExecutor redisLockExecutor;
    @Mock
    SubscribeFinder subscribeFinder;
    @Mock
    MembershipFinder membershipFinder;
    @Mock
    PaymentFinder paymentFinder;
    @Mock
    MembershipRepository membershipRepository;
    @Mock
    SubscribeRepository subscribeRepository;
    @Mock
    PaymentRepository paymentRepository;
    @Mock
    PaymentExpireScheduler paymentExpireScheduler;

    @InjectMocks SubscribeService subscribeService;

    private AuthMember auth;
    private Membership membership;

    @BeforeEach
    void setUp() {
        auth = new AuthMember(
                1L,
                "test@example.com",
                "testman~",
                "123-1234-1234",
                "인천어딘가",
                UserRole.ROLE_USER,
                "local"
        );

        // Membership 준비
        membership = Membership.builder()
                .name("test")
                .price(1234)
                .quantity(2)
                .year(Year.now().getValue())
                .build();
        given(membershipFinder.findById(1L)).willReturn(membership);

        // 중복 검사 무시
        willDoNothing().given(paymentFinder)
                .existsPendingPayment(any(), anyLong(), eq(PaymentStatus.PENDING));
        willDoNothing().given(subscribeFinder)
                .hasValidSubscription(any(), anyLong());
    }


    @Test
    void 구독_생성시_락을사용해동시성제어한다() {

        //given
        doAnswer(inv -> {
            Runnable task = inv.getArgument(3, Runnable.class);
            task.run();
            return null;
        }).when(redisLockExecutor)
                .executeWithLock(anyString(), anyLong(), anyLong(), any(Runnable.class));

        given(subscribeRepository.save(any(Subscribe.class)))
                .willAnswer(inv -> {
                    Subscribe s = inv.getArgument(0);
                    ReflectionTestUtils.setField(s, "id", 1L);
                    return s;
                });
        given(paymentRepository.save(any(Payment.class)))
                .willAnswer(inv -> inv.getArgument(0));

        // when
        SubscribeResponseDto result = subscribeService.saveSubscribe(auth, 1L);

        // then
        verify(paymentExpireScheduler).scheduleExpire(
                anyLong(),
                eq(Duration.ofMinutes(20))
        );

    }

    @Test
    void 구독생성_락획득실패시_예외를던진다() {
        // given
        reset(redisLockExecutor);

        willThrow(new IllegalStateException("락 획득 실패"))
                .given(redisLockExecutor)
                .executeWithLock(anyString(), anyLong(), anyLong(), any(Runnable.class));

        // when / then
        assertThatThrownBy(() -> subscribeService.saveSubscribe(auth, 1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("락 획득 실패");
    }
}
