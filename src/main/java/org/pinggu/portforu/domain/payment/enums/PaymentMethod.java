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
    UNKNOWN("알수없음");

    private final String tossName;

    PaymentMethod(String tossName) {
        this.tossName = tossName;
    }

    public static PaymentMethod fromTossMethod(String tossMethod) {
        if (tossMethod == null) return UNKNOWN;

        for (PaymentMethod method : values()) {
            if (method.tossName.equalsIgnoreCase(tossMethod)) {
                return method;
            }
        }
        return UNKNOWN;
    }
}

