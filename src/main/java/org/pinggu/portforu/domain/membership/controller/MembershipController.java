package org.pinggu.portforu.domain.membership.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.annotation.Admin;
import org.pinggu.portforu.common.domain.PageInfo;
import org.pinggu.portforu.common.domain.Pagecond;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.domain.membership.dto.request.MembershipSaveRequestDto;
import org.pinggu.portforu.domain.membership.dto.request.MembershipUpdateRequestDto;
import org.pinggu.portforu.domain.membership.dto.response.MembershipResponseDto;
import org.pinggu.portforu.domain.membership.service.MembershipService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/memberships")
public class MembershipController {
    private final MembershipService membershipService;

    @Admin
    @PostMapping
    public ResponseEntity<ApiResponse<MembershipResponseDto>> saveMembership(
            @Valid @RequestBody MembershipSaveRequestDto request
    ) {
        MembershipResponseDto response = membershipService.saveMembership(request);
        return ResponseEntity.ok().body(ApiResponse.of(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MembershipResponseDto>>> findAllMemberships(
            @ModelAttribute Pagecond pagecond
    ) {
        Page<MembershipResponseDto> responses = membershipService.findAllMemberships(pagecond);
        PageInfo pageInfo = PageInfo.builder()
                .pageNum(pagecond.getPageNum())
                .pageSize(pagecond.getPageSize())
                .totalElement(responses.getTotalElements())
                .totalPage(responses.getTotalPages())
                .build();
        return ResponseEntity.ok().body(ApiResponse.of(responses.getContent(), pageInfo));
    }

    @GetMapping("/{membershipId}")
    public ResponseEntity<ApiResponse<MembershipResponseDto>> findMembershipById(
            @PathVariable Long membershipId
    ) {
        MembershipResponseDto response = membershipService.findMembershipById(membershipId);
        return ResponseEntity.ok().body(ApiResponse.of(response));
    }

    @Admin
    @PutMapping("/{membershipId}")
    public ResponseEntity<ApiResponse<MembershipResponseDto>> updateMembership(
            @PathVariable Long membershipId,
            @Valid @RequestBody MembershipUpdateRequestDto request
    ) {
        MembershipResponseDto updatedMembership = membershipService.updateMembership(membershipId, request);
        return ResponseEntity.ok(ApiResponse.of(updatedMembership));
    }

    @Admin
    @DeleteMapping("/{membershipId}")
    public ResponseEntity<ApiResponse<MembershipResponseDto>> deleteMembership(
            @PathVariable Long membershipId
    ) {
        MembershipResponseDto response = membershipService.deleteMembership(membershipId);
        return ResponseEntity.ok().body(ApiResponse.of(response));
    }
}
