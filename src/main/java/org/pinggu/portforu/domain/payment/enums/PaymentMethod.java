package org.pinggu.portforu.domain.payment.enums;

public enum PaymentMethod {
    CARD("카드"),
    VIRTUAL_ACCOUNT("가상계좌"),
    ACCOUNT_TRANSFER("계좌이체"),
    MOBILE_PHONE("휴대폰"),
    TOSS_PAY("간편결제"),
    CULTURE_GIFT("문화상품권"),
    BOOK_GIFT("도서문화상품권"),
    GAME_GIFT("게임문화상품권"),
    UNKNOWN("알수없음"),
    SAMSUNG_PAY("삼성페이"),
    NAVER_PAY("네이버페이"),
    LPAY("엘페이");

    private final String tossName;

    PaymentMethod(String tossName) {
        this.tossName = tossName;
    }

    public static PaymentMethod fromTossMethod(String method, String provider) {
        // 삼성페이 결제해도 토스페이로 이넘값들어오는거 방지
        if ("간편결제".equalsIgnoreCase(method)) {
            if ("삼성페이".equalsIgnoreCase(provider)) return PaymentMethod.SAMSUNG_PAY;
            if ("네이버페이".equalsIgnoreCase(provider)) return PaymentMethod.NAVER_PAY;
            if ("엘페이".equalsIgnoreCase(provider)) return PaymentMethod.LPAY;
            if ("토스페이".equalsIgnoreCase(provider)) return PaymentMethod.TOSS_PAY;
            return TOSS_PAY; // default
        }

        for (PaymentMethod m : values()) {
            if (m.tossName.equalsIgnoreCase(method)) {
                return m;
            }
        }
        return UNKNOWN;
    }
}