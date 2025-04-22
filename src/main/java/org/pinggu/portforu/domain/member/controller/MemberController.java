package org.pinggu.portforu.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.annotation.Member;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.domain.member.dto.request.MemberDeleteRequestDto;
import org.pinggu.portforu.domain.member.dto.request.PasswordUpdateRequestDto;
import org.pinggu.portforu.domain.member.dto.response.MemberResponseDto;
import org.pinggu.portforu.domain.member.dto.request.MemberUpdateRequestDto;
import org.pinggu.portforu.domain.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @Member
    @GetMapping
    public ResponseEntity<ApiResponse<MemberResponseDto>> findMember(
            @AuthenticationPrincipal AuthMember authMember
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(memberService.findMember(authMember)));
    }

    @Member
    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateMember(
            @AuthenticationPrincipal AuthMember authMember,
            @Valid @RequestBody MemberUpdateRequestDto requestDto
    ) {
        memberService.updateMember(authMember, requestDto);

        return ResponseEntity.ok(ApiResponse.of("회원 정보 수정이 완료되었습니다."));
    }

    @Member
    @PutMapping("/password")
    public ResponseEntity<ApiResponse<String>> updatePassword(
            @AuthenticationPrincipal AuthMember authMember,
            @Valid @RequestBody PasswordUpdateRequestDto requestDto
    ) {
        memberService.updatePassword(authMember, requestDto);

        return ResponseEntity.ok(ApiResponse.of("비밀번호 수정이 완료되었습니다."));
    }

    @Member
    @DeleteMapping
    public ResponseEntity<ApiResponse<Long>> deleteMember(
            @AuthenticationPrincipal AuthMember authMember,
            @Valid @RequestBody MemberDeleteRequestDto requestDto
    ) {
        return ResponseEntity.ok(ApiResponse.of(memberService.deleteMember(authMember, requestDto)));
    }

}