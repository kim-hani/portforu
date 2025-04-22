package org.pinggu.portforu.domain.member.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/members")
public class AdminController {

    private final AdminService adminService;

    @Admin
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberResponseDto>> findMember(
            @PathVariable("memberId") Long memberId
    ) {
        return ResponseEntity.ok(ApiResponse.of(adminService.findMember(memberId)));
    }

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

        return ResponseEntity.ok().body(ApiResponse.of(responses.getContent(), pageInfo));
    }

    @Admin
    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Long>> deleteMember(
            @PathVariable("memberId") Long memberId
    ) {
        return ResponseEntity.ok(ApiResponse.of(adminService.deleteMember(memberId)));
    }

}