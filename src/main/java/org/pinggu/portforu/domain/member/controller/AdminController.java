package org.pinggu.portforu.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.annotation.Admin;
import org.pinggu.portforu.common.domain.PageInfo;
import org.pinggu.portforu.common.domain.Pagecond;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.domain.member.dto.response.MemberResponseDto;
import org.pinggu.portforu.domain.member.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "회원 관리 API", description = "관리자가 회원 정보를 조회·삭제합니다.")
@RestController
@RequestMapping("/api/v1/admin/members")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "회원 상세 조회", description = "ID로 회원 정보를 조회합니다.")
    @Admin
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberResponseDto>> findMember(
            @PathVariable Long memberId
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                adminService.findMember(memberId)
        ));
    }

    @Operation(summary = "회원 목록 조회", description = "페이지네이션된 회원 리스트를 반환합니다.")
    @Admin
    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberResponseDto>>> findAllMembers(
            @ModelAttribute Pagecond pagecond
    ) {
        Page<MemberResponseDto> responses = adminService.findAllMembers(pagecond);
        PageInfo pageInfo = PageInfo.builder()
                .pageNum(pagecond.getPageNum())
                .pageSize(pagecond.getPageSize())
                .totalElement(responses.getTotalElements())
                .totalPage(responses.getTotalPages())
                .build();

        return ResponseEntity.ok(ApiResponse.of(responses.getContent(), pageInfo));
    }

    @Operation(summary = "회원 삭제", description = "ID로 회원을 삭제합니다.")
    @Admin
    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Long>> deleteMember(
            @PathVariable Long memberId
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                adminService.deleteMember(memberId)
        ));
    }
}