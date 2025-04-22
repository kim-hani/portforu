package org.pinggu.portforu.config;

import org.pinggu.portforu.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class OrderUtils {

    public static Long extractSubscribeIdFromOrderId(String orderId) {
        try {
            String[] tokens = orderId.split("_");
            if (tokens.length < 2) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "잘못된 orderId 형식입니다.");
            }
            return Long.parseLong(tokens[1]);
        } catch (NumberFormatException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "orderId에서 subscribeId 추출 실패: 숫자 아님");
        }
    }
}
