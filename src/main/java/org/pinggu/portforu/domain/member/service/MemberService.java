package org.pinggu.portforu.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.member.dto.request.MemberDeleteRequestDto;
import org.pinggu.portforu.domain.member.dto.response.MemberResponseDto;
import org.pinggu.portforu.domain.member.dto.request.MemberUpdateRequestDto;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.member.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public MemberResponseDto findMember(AuthMember authMember) {
        Member member = findMemberByAuthMemberId(authMember);
        return MemberResponseDto.from(member);
    }

    @Transactional
    public MemberResponseDto updateMember(AuthMember authMember, MemberUpdateRequestDto requestDto) {
        Member member = findMemberByAuthMemberId(authMember);

        if(!passwordEncoder.matches(requestDto.getOldPassword(), member.getPassword())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "기존 비밀번호가 일치하지 않습니다.");
        }

        if (passwordEncoder.matches(requestDto.getNewPassword(), member.getPassword())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "동일한 비밀번호로 변경할수 없습니다.");
        }

        String newEncodedPassword = passwordEncoder.encode(requestDto.getNewPassword());

        member.update(newEncodedPassword, requestDto.getName(), requestDto.getPhoneNumber(), requestDto.getAddress());

        return MemberResponseDto.from(member);
    }

    @Transactional
    public void deleteMember(AuthMember authMember, MemberDeleteRequestDto requestDto) {
        Member member = findMemberByAuthMemberId(authMember);

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다.");
        }

        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "비밀번호 확인에 실패했습니다.");
        }

        member.delete();
    }

    private Member findMemberByAuthMemberId(AuthMember authMember) {
        return memberRepository.findById(authMember.getId()).orElseThrow(() ->
                new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 회원정보입니다."));
    }
}
