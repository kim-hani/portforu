package org.pinggu.portforu.domain.payment.exception;

import org.pinggu.portforu.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class PaymentFailedException extends CustomException {
    public PaymentFailedException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
