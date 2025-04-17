package org.pinggu.portforu.domain.subscribe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.member.repository.MemberRepository;
import org.pinggu.portforu.domain.membership.entity.Membership;
import org.pinggu.portforu.domain.membership.repository.MembershipRepository;
import org.pinggu.portforu.domain.payment.entity.Payment;
import org.pinggu.portforu.domain.payment.enums.PaymentStatus;
import org.pinggu.portforu.domain.payment.repository.PaymentRepository;
import org.pinggu.portforu.domain.subscribe.dto.request.SubscribeRequestDto;
import org.pinggu.portforu.domain.subscribe.dto.response.SubscribeResponseDto;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.pinggu.portforu.domain.subscribe.enums.SubscribeStatus;
import org.pinggu.portforu.domain.subscribe.repository.SubscribeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.ZoneId;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public SubscribeResponseDto saveSubscribe(Long memberId, Long membershipId, SubscribeRequestDto requestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 회원이 존재하지 않습니다."));
        Membership membership = membershipRepository.findByIdAndDeletedAtIsNull(membershipId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 멤버십이 존재하지 않습니다."));

        if (paymentRepository.existsBySubscribe_Member_IdAndSubscribe_Membership_IdAndStatus(
                memberId, membershipId, PaymentStatus.PENDING)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "결제가 진행 중인 구독이 존재합니다. 결제가 완료된 후 다시 시도하십시오.");
        }

        if (subscribeRepository.hasValidSubscription(memberId, membershipId)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 구독한 멤버십입니다.");
        }

        long count = subscribeRepository.countActiveByMembership(membership, Instant.now());
        if (count >= membership.getQuantity()) {
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

        Subscribe savedSub = subscribeRepository.save(subscribe);

        Payment payment = Payment.builder()
                .paymentMethod(requestDto.getPaymentMethod())
                .status(PaymentStatus.PENDING)
                .subscribe(savedSub)
                .build();

        paymentRepository.save(payment);

        return SubscribeResponseDto.from(savedSub, payment);
    }

    @Transactional(readOnly = true)
    public Page<SubscribeResponseDto> findSubscribes(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 회원이 존재하지 않습니다."));

        return subscribeRepository.findAllByMemberAndDeletedAtIsNull(member, pageable)
                .map(subscribe -> {
                    Payment payment = paymentRepository.findBySubscribe(subscribe)
                            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "결제 정보가 없습니다."));
                    return SubscribeResponseDto.from(subscribe, payment);
                });
    }

    @Transactional
    public Long deleteSubscribe(Long memberId, Long subscribeId) {
        Subscribe subscribe = subscribeRepository.findById(subscribeId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 구독이 존재하지 않습니다."));

        if (!subscribe.getMember().getId().equals(memberId)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "내 구독만 취소할 수 있습니다.");
        }

        if (subscribe.isDeleted()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 삭제된 구독입니다.");
        }

        Payment payment = paymentRepository.findBySubscribe(subscribe)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "결제 정보가 없습니다."));

        if (payment.getStatus() == PaymentStatus.PENDING) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "결제가 진행되지 않은 구독은 취소할 수 없습니다.");
        }

        subscribe.cancel();
        subscribeRepository.save(subscribe);

        log.info("구독 취소됨: subscribeId={}, memberId={}, 상태={}", subscribeId, memberId, subscribe.getStatus());

        return subscribe.getId();
    }

    @Transactional
    public void updateSubscriptionStatus(Long subscribeId, PaymentStatus paymentStatus) {
        Subscribe subscribe = subscribeRepository.findById(subscribeId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 구독을 찾을 수 없습니다."));

        if (paymentStatus == PaymentStatus.COMPLETED) {
            subscribe.activate();
            subscribeRepository.save(subscribe);
            log.info("구독 활성화 완료: subscribeId={}, 상태={}", subscribeId, subscribe.getStatus());
        } else if (paymentStatus == PaymentStatus.FAILED) {
            subscribe.fail();
            subscribeRepository.save(subscribe);
            log.info("구독 실패 처리됨: subscribeId={}, 상태={}", subscribeId, subscribe.getStatus());
        } else if (paymentStatus == PaymentStatus.EXPIRED) {
            subscribe.expire();
            subscribeRepository.save(subscribe);
            log.info("구독 만료 처리됨: subscribeId={}, 상태={}", subscribeId, subscribe.getStatus());
        }
    }

    @Transactional(readOnly = true)
    public Subscribe findById(Long subscribeId) {
        return subscribeRepository.findById(subscribeId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "구독 정보를 찾을 수 없습니다."));
    }
}
