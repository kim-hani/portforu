package org.pinggu.portforu.domain.subscribe.enums;

public enum SubscribeStatus {
    PENDING, // 결제 대기 (초기 상태)
    ACTIVE, // 결제 성공 후 정식 구독
    CANCELED, // 유저가 취소했지만 유효기간 내
    EXPIRED, // 만료된 구독
    FAILED // 구독 실패
}