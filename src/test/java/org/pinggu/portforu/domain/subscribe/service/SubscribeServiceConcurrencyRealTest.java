package org.pinggu.portforu.domain.subscribe.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.common.lock.RedisLockExecutor;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.pinggu.portforu.domain.membership.entity.Membership;
import org.pinggu.portforu.domain.membership.service.MembershipFinder;
import org.pinggu.portforu.domain.payment.service.PaymentFinder;
import org.pinggu.portforu.domain.subscribe.repository.SubscribeRepository;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Year;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class SubscribeServiceConcurrencyRealTest {

    @Mock
    private RedisLockExecutor redisLockExecutor;
    @Mock
    private SubscribeFinder subscribeFinder;
    @Mock
    private MembershipFinder membershipFinder;
    @Mock
    private PaymentFinder paymentFinder;
    @Mock
    private SubscribeRepository subscribeRepository;

    @InjectMocks
    private SubscribeService subscribeService;

    private Membership membership;
    private AuthMember authMember;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        membership = Membership.builder()
                .name("테스트 멤버십")
                .price(12345)
                .quantity(5)
                .year(Year.now().getValue())
                .build();

        // id는 리플렉션으로 강제 세팅
        ReflectionTestUtils.setField(membership, "id", 1L);

        authMember = new AuthMember(
                1L,
                "test@example.com",
                "테스트유저",
                "010-1234-5678",
                "서울",
                UserRole.ROLE_USER,
                "local"
        );

        given(membershipFinder.findById(1L)).willReturn(membership);

        // 락이 걸리면 Runnable 즉시 실행되도록 설정
        doAnswer(invocation -> {
            Runnable task = invocation.getArgument(3);
            task.run();
            return null;
        }).when(redisLockExecutor)
                .executeWithLock(anyString(), anyLong(), anyLong(), any(Runnable.class));
    }

    @Test
    void 구독_동시요청시_정원이초과되지_않는가() throws InterruptedException {
        // given
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        Long membershipId = 1L;

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    subscribeService.saveSubscribe(authMember, membershipId);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // then
        System.out.println("최종 남은 멤버십 수량 = " + membership.getQuantity());
        System.out.println("성공한 구독 수 = " + successCount.get());
        System.out.println("실패한 구독 수 = " + failCount.get());

        assertThat(membership.getQuantity()).isGreaterThanOrEqualTo(0);
        assertThat(successCount.get()).isLessThanOrEqualTo(5); // 초기 정원이 5니까
        assertThat(successCount.get() + failCount.get()).isEqualTo(threadCount); // 총 요청 수와 일치해야 함
    }

}

