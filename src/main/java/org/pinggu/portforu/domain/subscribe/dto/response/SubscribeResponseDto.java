package org.pinggu.portforu.domain.subscribe.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pinggu.portforu.domain.payment.entity.Payment;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;

import java.time.Instant;

@Getter
@Builder
public class SubscribeResponseDto {
    private final Long id;
    private final Long memberId;
    private final Long membershipId;
    private final String paymentMethod;
    private final Long paymentId;
    private final Instant startDate;
    private final Instant endDate;
    private final boolean active;
    private final String status;

    public static SubscribeResponseDto from(Subscribe subscribe, Payment payment) {
        return SubscribeResponseDto.builder()
                .id(subscribe.getId())
                .memberId(subscribe.getMember().getId())
                .membershipId(subscribe.getMembership().getId())
                .paymentMethod(payment.getPaymentMethod() != null ? payment.getPaymentMethod().name() : null)
                .paymentId(payment.getId())
                .startDate(subscribe.getStartDate())
                .endDate(subscribe.getEndDate())
                .active(subscribe.isActive())
                .status(subscribe.getStatus().name())
                .build();
    }

}