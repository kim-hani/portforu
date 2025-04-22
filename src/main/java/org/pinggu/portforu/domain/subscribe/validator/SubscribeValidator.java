package org.pinggu.portforu.domain.subscribe.validator;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.member.repository.MemberRepository;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.pinggu.portforu.domain.subscribe.repository.SubscribeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubscribeValidator {

    private final SubscribeRepository subscribeRepository;
    private final MemberRepository memberRepository;

    public boolean isSubscribed(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."));

        List<Subscribe> subscriptions = subscribeRepository
                .findAllByMember(member, Sort.unsorted());

        return subscriptions.stream()
                .filter(sub -> sub.getStartDate().isBefore(Instant.now()))
                .anyMatch(sub -> sub.getEndDate().isAfter(Instant.now()));
    }

}
