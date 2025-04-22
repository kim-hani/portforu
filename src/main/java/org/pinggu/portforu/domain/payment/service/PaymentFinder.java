package org.pinggu.portforu.domain.payment.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.payment.entity.Payment;
import org.pinggu.portforu.domain.payment.enums.PaymentStatus;
import org.pinggu.portforu.domain.payment.repository.PaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentFinder {

    private final PaymentRepository paymentRepository;

    public Payment findBySubscribeId(Long subscribeId) {
        return paymentRepository.findBySubscribeId(subscribeId)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "결제 정보가 없습니다."));
    }

    public void existsPayment (
            Member member, Long membershipId, PaymentStatus paymentStatus
    ) {
        if (paymentRepository.existsPayment(
                member.getId(), membershipId, PaymentStatus.PENDING)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "결제가 진행 중인 구독이 존재합니다. 결제가 완료된 후 다시 시도하십시오.");
        }
    }

}
