package org.pinggu.portforu.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "회원 API", description = "회원 본인 정보 조회·수정·삭제")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "내 정보 조회", description = "로그인 된 회원 본인의 정보를 조회합니다.")
    @Member
    @GetMapping
    public ResponseEntity<ApiResponse<MemberResponseDto>> findMember(
            @AuthenticationPrincipal AuthMember authMember
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                memberService.findMember(authMember)
        ));
    }

    @Operation(summary = "내 정보 수정", description = "회원 본인의 정보를 수정합니다.")
    @Member
    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateMember(
            @AuthenticationPrincipal AuthMember authMember,
            @Valid @RequestBody MemberUpdateRequestDto requestDto
    ) {
        memberService.updateMember(authMember, requestDto);
        return ResponseEntity.ok(ApiResponse.of("회원 정보 수정이 완료되었습니다."));
    }

    @Operation(summary = "비밀번호 변경", description = "회원 본인의 비밀번호를 변경합니다.")
    @Member
    @PutMapping("/password")
    public ResponseEntity<ApiResponse<String>> updatePassword(
            @AuthenticationPrincipal AuthMember authMember,
            @Valid @RequestBody PasswordUpdateRequestDto requestDto
    ) {
        memberService.updatePassword(authMember, requestDto);
        return ResponseEntity.ok(ApiResponse.of("비밀번호 수정이 완료되었습니다."));
    }

    @Operation(summary = "회원 탈퇴", description = "회원 본인이 계정을 삭제합니다.")
    @Member
    @DeleteMapping
    public ResponseEntity<ApiResponse<Long>> deleteMember(
            @AuthenticationPrincipal AuthMember authMember,
            @Valid @RequestBody MemberDeleteRequestDto requestDto
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                memberService.deleteMember(authMember, requestDto)
        ));
    }
}