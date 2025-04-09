package org.pinggu.portforu.domain.subscribe.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pinggu.portforu.domain.payment.enums.PaymentMethod;

@Getter
@AllArgsConstructor
public class SubscribeRequestDto {

    @NotNull(message = "결제 수단을 입력하세요.")
    private PaymentMethod paymentMethod;

}
