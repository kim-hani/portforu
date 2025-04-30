package org.pinggu.portforu.domain.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.common.lock.RedisLockExecutor;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.pinggu.portforu.domain.membership.entity.Membership;
import org.pinggu.portforu.domain.payment.dto.response.TossPaymentConfirmResponseDto;
import org.pinggu.portforu.domain.payment.entity.Payment;
import org.pinggu.portforu.domain.payment.enums.PaymentStatus;
import org.pinggu.portforu.domain.payment.repository.PaymentRepository;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.pinggu.portforu.domain.subscribe.repository.SubscribeRepository;
import org.pinggu.portforu.domain.subscribe.service.SubscribeService;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.Year;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
//redis mock로 가상으로 적용버전
class PaymentServiceConcurrencyTest {

    @Mock
    private RedisLockExecutor redisLockExecutor;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private SubscribeRepository subscribeRepository;
    @Mock
    private SubscribeService subscribeService;
    @Mock
    private RestTemplate restTemplate;

    private PaymentFinder paymentFinder;
    private PaymentService paymentService;

    private Membership membership;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        paymentFinder = new PaymentFinder(paymentRepository);
        paymentService = new PaymentService(
                restTemplate,
                paymentRepository,
                paymentFinder,
                subscribeService,
                subscribeRepository,
                redisLockExecutor
        );

        doAnswer(inv -> {
            Runnable task = inv.getArgument(3);
            task.run();
            return null;
        }).when(redisLockExecutor).executeWithLock(anyString(), anyLong(), anyLong(), any(Runnable.class));

        doAnswer(invocation -> {
            Long subscribeId = invocation.getArgument(0);
            PaymentStatus status = invocation.getArgument(1);

            if (status == PaymentStatus.COMPLETED) {
                synchronized (membership) {
                    membership.decreaseQuantity();
                }
            }
            return null;
        }).when(subscribeService).updateSubscriptionStatus(anyLong(), any(PaymentStatus.class));

        given(restTemplate.postForEntity(anyString(), any(), eq(TossPaymentConfirmResponseDto.class)))
                .willReturn(ResponseEntity.ok(
                        TossPaymentConfirmResponseDto.builder()
                                .method("카드")
                                .easyPay(null)
                                .build()
                ));

        membership = Membership.builder()
                .name("testmembership")
                .price(1234)
                .quantity(5)
                .year(Year.now().getValue())
                .build();
        ReflectionTestUtils.setField(membership, "id", 1L);
    }

    @Test
    void 결제_완료_동시요청시_멤버십_정원이_초과되지_않는다() throws InterruptedException {
        int threadCount = 10000;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            int idx = i;
            executor.submit(() -> {
                try {
                    Long subscribeId = (long) (idx + 1);

                    Member member = Member.builder()
                            .email("user" + idx + "@test.com").password("1234").name("user" + idx)
                            .phoneNumber("123-1234-1234").address("인천어딘가").userRole(UserRole.ROLE_USER)
                            .provider("local")
                            .build();

                    Subscribe localSubscribe = Subscribe.builder()
                            .member(member)
                            .membership(membership)
                            .startDate(Instant.now())
                            .endDate(Instant.now().plusSeconds(3600))
                            .build();
                    ReflectionTestUtils.setField(localSubscribe, "id", subscribeId);

                    Payment localPayment = Payment.builder()
                            .status(PaymentStatus.PENDING)
                            .subscribe(localSubscribe)
                            .build();

                    given(paymentRepository.findBySubscribeId(eq(subscribeId))).willReturn(Optional.of(localPayment));

                    given(subscribeRepository.findById(eq(subscribeId))).willReturn(Optional.of(localSubscribe));

                    paymentService.handleSuccessPayment("payKey_" + idx, "order_" + idx + "_" + subscribeId, 10000L);
                    successCount.incrementAndGet();
                } catch (CustomException e) {
                    failCount.incrementAndGet();
                } catch (Exception e) {
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        System.out.println("성공한 결제 수 = " + successCount.get());
        System.out.println("실패한 결제 수 = " + failCount.get());
        System.out.println("남은 정원 = " + membership.getQuantity());

        assertThat(successCount.get()).isLessThanOrEqualTo(5);
        assertThat(successCount.get() + failCount.get()).isEqualTo(threadCount);
        assertThat(membership.getQuantity()).isEqualTo(5 - successCount.get());
    }

    @Test
    void 중복결제_방지_동시요청() throws InterruptedException {
        // given
        Long subscribeId = 1L;

        Member member = Member.builder()
                .email("user@test.com").password("1234").name("user")
                .phoneNumber("123-1234-1234").address("인천")
                .userRole(UserRole.ROLE_USER)
                .provider("local")
                .build();

        Membership membership = Membership.builder()
                .name("testmembership")
                .price(10000)
                .quantity(5)
                .year(Year.now().getValue())
                .build();
        ReflectionTestUtils.setField(membership, "id", 1L);

        Subscribe subscribe = Subscribe.builder()
                .member(member)
                .membership(membership)
                .startDate(Instant.now())
                .endDate(Instant.now().plusSeconds(3600))
                .build();
        ReflectionTestUtils.setField(subscribe, "id", subscribeId);

        Payment payment = Payment.builder()
                .status(PaymentStatus.PENDING)
                .subscribe(subscribe)
                .build();

        given(paymentRepository.findBySubscribeId(eq(subscribeId))).willReturn(Optional.of(payment));
        given(subscribeRepository.findById(eq(subscribeId))).willReturn(Optional.of(subscribe));
        given(restTemplate.postForEntity(anyString(), any(), eq(TossPaymentConfirmResponseDto.class)))
                .willReturn(ResponseEntity.ok(
                        TossPaymentConfirmResponseDto.builder()
                                .method("카드")
                                .easyPay(null)
                                .build()
                ));

        // when
        int threadCount = 10000;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            int finalIdx = i;
            executor.submit(() -> {
                try {
                    paymentService.handleSuccessPayment(
                            "payKey_" + finalIdx,
                            "order_" + finalIdx + "_" + subscribeId,
                            10000L
                    );
                    successCount.incrementAndGet();
                } catch (CustomException e) {
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // then
        System.out.println("성공한 결제 수 = " + successCount.get());
        System.out.println("실패한 결제 수 = " + failCount.get());

        assertThat(successCount.get()).isEqualTo(1);
        assertThat(failCount.get()).isEqualTo(threadCount - 1);
    }

}