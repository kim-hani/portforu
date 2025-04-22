package org.pinggu.portforu.domain.membership.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.annotation.Admin;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.domain.membership.dto.request.MembershipSaveRequestDto;
import org.pinggu.portforu.domain.membership.dto.request.MembershipUpdateRequestDto;
import org.pinggu.portforu.domain.membership.dto.response.MembershipResponseDto;
import org.pinggu.portforu.domain.membership.service.MembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/memberships")
public class MembershipAdminController {

    private final MembershipService membershipService;

    @Admin
    @PostMapping
    public ResponseEntity<ApiResponse<MembershipResponseDto>> saveMembership(
            @Valid @RequestBody MembershipSaveRequestDto requestDto
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(membershipService.saveMembership(requestDto)));
    }

    @Admin
    @PutMapping("/{membershipId}")
    public ResponseEntity<ApiResponse<String>> updateMembership(
            @PathVariable("membershipId") Long membershipId,
            @Valid @RequestBody MembershipUpdateRequestDto requestDto
    ) {
        membershipService.updateMembership(membershipId, requestDto);

        return ResponseEntity.ok(ApiResponse.of("멤버십 수정이 완료되었습니다."));
    }

    @Admin
    @DeleteMapping("/{membershipId}")
    public ResponseEntity<ApiResponse<Long>> deleteMembership(
            @PathVariable("membershipId") Long membershipId
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(membershipService.deleteMembership(membershipId)));
    }

}
