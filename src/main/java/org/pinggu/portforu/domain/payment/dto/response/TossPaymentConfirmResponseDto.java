package org.pinggu.portforu.domain.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TossPaymentConfirmResponseDto {
    private String method;
    private TossEasyPay easyPay;

}