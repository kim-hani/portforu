package org.pinggu.portforu.domain.subscribe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.pinggu.portforu.domain.payment.entity.Payment;

@Getter
@Builder
@AllArgsConstructor
public class SubscribeRequestDto {
    private final Payment.PaymentMethod paymentMethod; // 결제 수단
}
