package org.pinggu.portforu.domain.subscribe.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.pinggu.portforu.domain.payment.enums.PaymentMethod;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubscribeRequestDto {

    @NotNull(message = "결제 수단을 입력하세요.")
    private PaymentMethod paymentMethod;

}
