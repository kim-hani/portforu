package org.pinggu.portforu.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.member.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberFinder {

    private final MemberRepository memberRepository;

    public void validateOwnership(AuthMember authMember, Long id) {
        if (!authMember.getId().equals(id)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다");
        }
    }

    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 회원정보입니다."));
    }

    public Member findMemberByUnsubscribeToken(String token) {
        return memberRepository.findByUnsubscribeToken(token)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "유효하지 않은 구독 해지 링크입니다."));
    }
}
