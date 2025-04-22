package org.pinggu.portforu.domain.subscribe.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.pinggu.portforu.domain.subscribe.repository.SubscribeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscribeFinder {

    private final SubscribeRepository subscribeRepository;

    public Subscribe findById(Long subscribeId) {
        Subscribe subscribe = subscribeRepository.findById(subscribeId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "구독 정보를 찾을 수 없습니다."));

        if (subscribe.getIsDeleted()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 삭제된 구독입니다.");
        }

        return subscribe;
    }

    public void hasValidSubscription (Member member, Long membershipId) {
        if (subscribeRepository.hasValidSubscription(member.getId(), membershipId)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 구독한 멤버십입니다.");
        }
    }

}
