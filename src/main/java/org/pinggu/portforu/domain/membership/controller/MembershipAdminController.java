package org.pinggu.portforu.domain.membership.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "멤버십 관리 API", description = "관리자가 멤버십을 생성·수정·삭제합니다.")
@RestController
@RequestMapping("/api/v1/admin/memberships")
@RequiredArgsConstructor
public class MembershipAdminController {

    private final MembershipService membershipService;

    @Operation(summary = "멤버십 생성", description = "새 멤버십을 등록합니다.")
    @Admin
    @PostMapping
    public ResponseEntity<ApiResponse<MembershipResponseDto>> saveMembership(
            @Valid @RequestBody MembershipSaveRequestDto requestDto
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                membershipService.saveMembership(requestDto)
        ));
    }

    @Operation(summary = "멤버십 수정", description = "기존 멤버십 정보를 수정합니다.")
    @Admin
    @PutMapping("/{membershipId}")
    public ResponseEntity<ApiResponse<String>> updateMembership(
            @PathVariable Long membershipId,
            @Valid @RequestBody MembershipUpdateRequestDto requestDto
    ) {
        membershipService.updateMembership(membershipId, requestDto);
        return ResponseEntity.ok(ApiResponse.of("멤버십 수정이 완료되었습니다."));
    }

    @Operation(summary = "멤버십 삭제", description = "기존 멤버십을 삭제합니다.")
    @Admin
    @DeleteMapping("/{membershipId}")
    public ResponseEntity<ApiResponse<Long>> deleteMembership(
            @PathVariable Long membershipId
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                membershipService.deleteMembership(membershipId)
        ));
    }
}
