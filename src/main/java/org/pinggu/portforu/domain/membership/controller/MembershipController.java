package org.pinggu.portforu.domain.membership.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.domain.membership.dto.response.MembershipResponseDto;
import org.pinggu.portforu.domain.membership.service.MembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "멤버십 조회 API", description = "멤버십 목록 및 상세 조회")
@RestController
@RequestMapping("/api/v1/memberships")
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    @Operation(summary = "멤버십 목록 조회", description = "사용 가능한 모든 멤버십을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<MembershipResponseDto>>> findAllMemberships() {
        List<MembershipResponseDto> list = membershipService.findAllMemberships();
        return ResponseEntity.ok(ApiResponse.of(list));
    }

    @Operation(summary = "멤버십 상세 조회", description = "ID로 특정 멤버십의 상세 정보를 조회합니다.")
    @GetMapping("/{membershipId}")
    public ResponseEntity<ApiResponse<MembershipResponseDto>> findMembershipById(
            @PathVariable Long membershipId
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                membershipService.findMembershipById(membershipId)
        ));
    }
}