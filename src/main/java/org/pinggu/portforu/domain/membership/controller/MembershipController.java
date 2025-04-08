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
        return ResponseEntity.ok().body(ApiResponse.of(membershipService.saveMembership(request)));
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
            @PathVariable("membershipId") Long membershipId
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(membershipService.findMembershipById(membershipId)));
    }

    @Admin
    @PutMapping("/{membershipId}")
    public ResponseEntity<ApiResponse<MembershipResponseDto>> updateMembership(
            @PathVariable("membershipId") Long membershipId,
            @Valid @RequestBody MembershipUpdateRequestDto request
    ) {
        return ResponseEntity.ok(ApiResponse.of(membershipService.updateMembership(membershipId, request)));
    }

    @Admin
    @DeleteMapping("/{membershipId}")
    public ResponseEntity<ApiResponse<Long>> deleteMembership(
            @PathVariable("membershipId") Long membershipId
    ) {
        Long deletedMembershipId = membershipService.deleteMembership(membershipId);

        return ResponseEntity.ok().body(ApiResponse.of(deletedMembershipId));
    }

}
