package org.pinggu.portforu.domain.subscribe.service;

import lombok.RequiredArgsConstructor;
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
import org.pinggu.portforu.domain.subscribe.repository.SubscribeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.Year;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public SubscribeResponseDto saveSubscribe(
            Long memberId, Long membershipId, SubscribeRequestDto requestDto
    ) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 회원이 존재하지 않습니다."));
        Membership membership = membershipRepository.findByIdAndDeletedAtIsNull(membershipId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 멤버십이 존재하지 않습니다."));

        int currentYear = Year.now().getValue();

        if (membership.getYear() != currentYear) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "해당 멤버십은 " + membership.getYear() + "년 전용입니다.");
        }

        if (subscribeRepository.existsByMemberIdAndMembershipId(memberId, membershipId)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 구독중인 멤버십입니다.");
        }

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.of(currentYear, 12, 31, 23, 59, 59);

        Subscribe subscribe = Subscribe.builder()
                .member(member)
                .membership(membership)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        Subscribe savedSub = subscribeRepository.save(subscribe);

        Payment payment = Payment.builder()
                .paymentMethod(requestDto.getPaymentMethod())
                .status(PaymentStatus.COMPLETED)
                .subscribe(savedSub)
                .build();
        paymentRepository.save(payment);

        return SubscribeResponseDto.from(savedSub, payment);
    }

    @Transactional(readOnly = true)
    public Page<SubscribeResponseDto> findSubscribes(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 회원이 존재하지 않습니다."));

        return subscribeRepository.findAllByMember(member, pageable)
                .map(subscribe -> {
                    Payment payment = paymentRepository.findBySubscribe(subscribe)
                            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "결제 정보가 없습니다."));

                    return SubscribeResponseDto.from(subscribe, payment);
                });
    }

    @Transactional
    public Long deleteSubscribe(Long memberId, Long subscribeId) {
        Subscribe subscribe = subscribeRepository.findByIdAndDeletedAtIsNull(subscribeId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 구독이 존재하지 않습니다."));

        if (!subscribe.getMember().getId().equals(memberId)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "내 구독만 취소할 수 있습니다.");
        }

        paymentRepository.findBySubscribe(subscribe)
                .ifPresent(paymentRepository::delete);

        return subscribe.delete();
    }

}