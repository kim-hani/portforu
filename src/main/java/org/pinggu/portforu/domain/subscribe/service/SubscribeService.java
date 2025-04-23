package org.pinggu.portforu.domain.subscribe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.common.lock.RedisLockExecutor;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.membership.entity.Membership;
import org.pinggu.portforu.domain.membership.repository.MembershipRepository;
import org.pinggu.portforu.domain.membership.service.MembershipFinder;
import org.pinggu.portforu.domain.payment.entity.Payment;
import org.pinggu.portforu.domain.payment.enums.PaymentStatus;
import org.pinggu.portforu.domain.payment.repository.PaymentRepository;
import org.pinggu.portforu.domain.payment.scheduler.PaymentExpireScheduler;
import org.pinggu.portforu.domain.payment.service.PaymentFinder;
import org.pinggu.portforu.domain.subscribe.dto.response.SubscribeResponseDto;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.pinggu.portforu.domain.subscribe.repository.SubscribeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final SubscribeFinder subscribeFinder;
    private final MembershipFinder membershipFinder;
    private final PaymentFinder paymentFinder;
    private final MembershipRepository membershipRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentExpireScheduler paymentExpireScheduler;
    private final RedisLockExecutor redisLockExecutor;
    // 구독 생성
    @Transactional
    public SubscribeResponseDto saveSubscribe(AuthMember authMember, Long membershipId) {
        Member member = Member.fromAuthMember(authMember);
        Membership membership = membershipFinder.findById(membershipId);

        paymentFinder.existsPayment(member, membershipId, PaymentStatus.PENDING);
        subscribeFinder.hasValidSubscription(member, membershipId);

        if (membership.getQuantity() <= 0) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "멤버십 정원이 초과되었습니다.");
        }

        int currentYear = Year.now().getValue();
        if (membership.getYear() != currentYear) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "해당 멤버십은 " + membership.getYear() + "년 전용입니다.");
        }

        Instant startDate = Instant.now();
        Instant endDate = LocalDateTime.of(currentYear, 12, 31, 23, 59, 59)
                .atZone(ZoneId.of("Asia/Seoul"))
                .toInstant();

        Subscribe subscribe = Subscribe.builder()
                .member(member)
                .membership(membership)
                .startDate(startDate)
                .endDate(endDate)
                .build();


        Subscribe savedSubscribe = subscribeRepository.save(subscribe);

        Payment payment = Payment.builder()
                .status(PaymentStatus.PENDING)
                .subscribe(savedSubscribe)
                .build();

        paymentRepository.save(payment);
        paymentExpireScheduler.scheduleExpire(savedSubscribe.getId(), Duration.ofMinutes(20));

        return SubscribeResponseDto.from(savedSubscribe, payment);
    }


    // 구독 조회
    @Transactional(readOnly = true)
    public List<SubscribeResponseDto> findAllSubscribes(AuthMember authMember) {
        Member member = Member.fromAuthMember(authMember);

        // Id 기준 내림차순
        List<Subscribe> subscribes = subscribeRepository.findAllByMember(member, Sort.by(Sort.Order.desc("id")));
        return subscribes.stream()
                .map(subscribe -> {
                    Payment payment = paymentFinder.findBySubscribeId(subscribe.getId());
                    return SubscribeResponseDto.from(subscribe, payment);
                })
                .collect(Collectors.toList());
    }


    // 구독 취소
    @Transactional
    public Long deleteSubscribe(Long memberId, Long subscribeId) {
        Subscribe subscribe = subscribeFinder.findById(subscribeId);

        if (!subscribe.getMember().getId().equals(memberId)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "내 구독만 취소할 수 있습니다.");
        }

        Payment payment = paymentFinder.findBySubscribeId(subscribeId);

        if (payment.getStatus() == PaymentStatus.PENDING) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "결제가 진행되지 않은 구독은 취소할 수 없습니다.");
        }

        // 취소는 정원 복구 안 함
        subscribe.cancel();
        return subscribe.getId();
    }

    // 결제 후 상태 업데이트
    @Transactional
    public void updateSubscriptionStatus(Long subscribeId, PaymentStatus paymentStatus) {
        Subscribe subscribe = subscribeFinder.findById(subscribeId);
        Membership membership = subscribe.getMembership();

        if (paymentStatus == PaymentStatus.COMPLETED) {
            subscribe.activate();
            membership.decreaseQuantity(); // 정원 감소
            membershipRepository.save(membership); //  DB 반영
            log.info("구독 활성화 완료: subscribeId={}, 상태={}", subscribeId, subscribe.getStatus());
        } else if (paymentStatus == PaymentStatus.FAILED) {
            subscribe.fail();
            log.info("구독 실패 처리됨: subscribeId={}, 상태={}", subscribeId, subscribe.getStatus());
        } else if (paymentStatus == PaymentStatus.EXPIRED) {
            subscribe.expire();
            membership.increaseQuantity(); // 정원 증가
            membershipRepository.save(membership); // DB 반영
            log.info("구독 만료 처리됨: subscribeId={}, 상태={}", subscribeId, subscribe.getStatus());
        }
    }
}