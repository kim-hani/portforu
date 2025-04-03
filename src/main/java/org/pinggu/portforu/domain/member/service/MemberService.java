package org.pinggu.portforu.domain.member.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.domain.member.dto.response.MemberResponseDto;
import org.pinggu.portforu.domain.member.dto.request.MemberUpdateRequestDto;
import org.pinggu.portforu.domain.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse<MemberResponseDto> findMember(AuthMember authMember) {
        return null;
    }

    public ApiResponse<MemberResponseDto> updateMember(AuthMember authMember, @Valid MemberUpdateRequestDto requestDto) {
        return null;
    }

    public void deleteMember(AuthMember authMember) {

    }
}
