package org.pinggu.portforu.domain.payment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.pinggu.portforu.common.lock.RedisLockExecutor;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.pinggu.portforu.domain.membership.entity.Membership;
import org.pinggu.portforu.domain.payment.entity.Payment;
import org.pinggu.portforu.domain.payment.enums.PaymentMethod;
import org.pinggu.portforu.domain.payment.enums.PaymentStatus;
import org.pinggu.portforu.domain.payment.repository.PaymentRepository;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.pinggu.portforu.domain.subscribe.enums.SubscribeStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.ZoneId;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class PaymentExpireServiceTest {

    @Mock private PaymentRepository paymentRepository;
    @Mock private RedisLockExecutor redisLockExecutor;
    @InjectMocks private PaymentExpireService expireService;

    private Payment pendingPayment;
    private Payment nonPendingPayment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // RedisLockExecutor.executeWithLock 호출 시 Runnable 즉시 실행
        doAnswer(invocation -> {
            Runnable task = invocation.getArgument(3, Runnable.class);
            task.run();
            return null;
        }).when(redisLockExecutor)
                .executeWithLock(anyString(), anyLong(), anyLong(), any(Runnable.class));

        // 테스트용 Member, Membership 준비
        Member member = Member.builder()
                .email("a@a.com").password("p").name("n")
                .phoneNumber("010").address("addr")
                .userRole(UserRole.ROLE_USER).provider("local")
                .build();
        Membership membership = Membership.builder()
                .name("Basic").price(1000).quantity(10)
                .year(Year.now().getValue()).build();

        Instant now = Instant.now();
        Instant later = LocalDateTime.of(Year.now().getValue(),12,31,23,59,59)
                .atZone(ZoneId.systemDefault()).toInstant();

        Subscribe sub1 = Subscribe.builder()
                .member(member).membership(membership)
                .startDate(now).endDate(later).build();
        Subscribe sub2 = Subscribe.builder()
                .member(member).membership(membership)
                .startDate(now).endDate(later).build();

        // pendingPayment (status==PENDING, paymentKey==null)
        pendingPayment = Payment.builder()
                .paymentMethod(PaymentMethod.UNKNOWN)
                .status(PaymentStatus.PENDING)
                .subscribe(sub1)
                .build();

        // nonPendingPayment (status!=PENDING or paymentKey!=null)
        nonPendingPayment = Payment.builder()
                .paymentMethod(PaymentMethod.UNKNOWN)
                .status(PaymentStatus.COMPLETED)
                .subscribe(sub2)
                .build();
        nonPendingPayment.assignPaymentKey("alreadyKey");
    }

    @Test
    void 결제만료처리_pending이고Key없으면_만료되고구독상태_실패로변경한다() {
        // given
        given(paymentRepository.findBySubscribeId(1L))
                .willReturn(Optional.of(pendingPayment));

        // when
        expireService.expireIfPending(1L);

        // then
        assertThat(pendingPayment.getStatus()).isEqualTo(PaymentStatus.EXPIRED);
        assertThat(pendingPayment.getSubscribe().getStatus())
                .isEqualTo(SubscribeStatus.FAILED);
    }

    @Test
    void 결제만료처리_pending아니면_아무동작도안한다() {
        // given
        given(paymentRepository.findBySubscribeId(2L))
                .willReturn(Optional.of(nonPendingPayment));

        // when
        expireService.expireIfPending(2L);

        // then
        assertThat(nonPendingPayment.getStatus()).isEqualTo(PaymentStatus.COMPLETED);
        assertThat(nonPendingPayment.getSubscribe().getStatus())
                .isEqualTo(SubscribeStatus.PENDING);
    }
}