package org.pinggu.portforu.domain.subscribe.scheduler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pinggu.portforu.common.lock.RedisLockExecutor;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.pinggu.portforu.domain.member.repository.MemberRepository;
import org.pinggu.portforu.domain.membership.entity.Membership;
import org.pinggu.portforu.domain.membership.repository.MembershipRepository;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.pinggu.portforu.domain.subscribe.enums.SubscribeStatus;
import org.pinggu.portforu.domain.subscribe.repository.SubscribeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("local")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Rollback
class SubscribeSchedulerConcurrencyTest {

    @Autowired
    private SubscribeScheduler subscribeScheduler;

    @Autowired
    private CanceledSubscribeScheduler canceledSubscribeScheduler;

    @Autowired
    private SubscribeRepository subscribeRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Membership membership;
    private final int threadCount = 10;

    @BeforeEach
    void setup() {
        membership = Membership.builder()
                .name("test")
                .price(1000)
                .quantity(5)
                .year(2025)
                .build();
        membership = membershipRepository.save(membership);

        Instant now = Instant.now();
        Instant end = now.minusSeconds(1);

        // ACTIVE 구독 10개
        for (int i = 0; i < threadCount; i++) {
            Member member = createMember();
            Subscribe sub = Subscribe.builder()
                    .member(member)
                    .membership(membership)
                    .startDate(now)
                    .endDate(end)
                    .build();
            sub.activate();
            subscribeRepository.save(sub);
        }

        // CANCELED 구독 10개
        for (int i = 0; i < threadCount; i++) {
            Member member = createMember();
            Subscribe sub = Subscribe.builder()
                    .member(member)
                    .membership(membership)
                    .startDate(now)
                    .endDate(end)
                    .build();
            sub.activate();
            sub.cancel();   // ACTIVE → CANCELED
            subscribeRepository.save(sub);
        }
    }

    private Member createMember() {
        String randomEmail = "test" + UUID.randomUUID() + "@email.com";
        Member member = Member.builder()
                .email(randomEmail)
                .name("testname")
                .address("테스트주소")
                .password("test")
                .phoneNumber("010-0000-0000")
                .provider("local")
                .userRole(UserRole.ROLE_USER)
                .build();
        return memberRepository.save(member);
    }

    @Test
    void ACTIVE_구독_만료_정원복구_동시성테스트() throws InterruptedException {
        // given
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    subscribeScheduler.expireEndedSubscriptions();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        // then
        Membership saved = membershipRepository.findById(membership.getId()).orElseThrow();
        // 원래 5 + ACTIVE 10건 복구
        assertThat(saved.getQuantity()).isEqualTo(5 + threadCount);

        long expiredActive = subscribeRepository.countByStatus(SubscribeStatus.EXPIRED);
        // ACTIVE → EXPIRED 10건
        assertThat(expiredActive).isEqualTo(threadCount);
    }

    @Test
    void CANCELED_구독_만료_정원복구_동시성테스트() throws InterruptedException {
        // given: 다시 쓰레드풀과 래치 초기화
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    canceledSubscribeScheduler.expireCanceledSubscriptions();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        // then
        Membership saved = membershipRepository.findById(membership.getId()).orElseThrow();
        assertThat(saved.getQuantity()).isEqualTo(5 + threadCount /*ACTIVE*/ + threadCount /*CANCELED*/);

        long expiredCanceled = subscribeRepository.countByStatus(SubscribeStatus.EXPIRED);
        // ACTIVE + CANCELED → EXPIRED 총 20건
        assertThat(expiredCanceled).isEqualTo(threadCount * 2);
    }
}
