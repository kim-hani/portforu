package org.pinggu.portforu.domain.subscribe.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.payment.entity.Payment;
import org.pinggu.portforu.domain.payment.repository.PaymentRepository;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class SubscribeResponseDto {
    private final Long id; // 구독 ID
    private final Long memberId; // 사용자 ID
    private final Long membershipId; // 멤버십 ID
    private final String paymentMethod; // 결제 수단
    private final Long paymentId; // 결제 ID
    private final LocalDateTime startDate; // 구독 시작일
    private final LocalDateTime endDate; // 구독 종료일
    private final boolean active;

    public static SubscribeResponseDto fromEntity(
            Subscribe subscribe, PaymentRepository paymentRepository
    ) {
        Payment payment = paymentRepository.findBySubscribe(subscribe)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "결제 정보가 없습니다."));

        boolean isActive = LocalDateTime.now().isBefore(subscribe.getEndDate());

        return SubscribeResponseDto.builder()
                .id(subscribe.getId())
                .memberId(subscribe.getMemberId())
                .membershipId(subscribe.getMembershipId())
                .paymentMethod(payment.getPaymentMethod().name())
                .paymentId(payment.getId())
                .startDate(subscribe.getStartDate())
                .endDate(subscribe.getEndDate())
                .active(isActive)
                .build();
    }

}
