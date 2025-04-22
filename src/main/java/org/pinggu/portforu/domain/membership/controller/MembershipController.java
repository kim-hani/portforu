package org.pinggu.portforu.domain.membership.controller;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.domain.membership.dto.response.MembershipResponseDto;
import org.pinggu.portforu.domain.membership.service.MembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/memberships")
public class MembershipController {
    private final MembershipService membershipService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MembershipResponseDto>>> findAllMemberships() {
        List<MembershipResponseDto> responses = membershipService.findAllMemberships();

        return ResponseEntity.ok().body(ApiResponse.of(responses));
    }

    @GetMapping("/{membershipId}")
    public ResponseEntity<ApiResponse<MembershipResponseDto>> findMembershipById(
            @PathVariable("membershipId") Long membershipId
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(membershipService.findMembershipById(membershipId)));
    }

}
