package org.pinggu.portforu.domain.subscribe.controller;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.annotation.Member;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.domain.subscribe.dto.response.SubscribeResponseDto;
import org.pinggu.portforu.domain.subscribe.service.SubscribeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscribes")
public class SubscribeController {

    private final SubscribeService subscribeService;

    @Member
    @PostMapping("/{membershipId}")
    public ResponseEntity<ApiResponse<SubscribeResponseDto>> saveSubscribe(
            @PathVariable("membershipId") Long membershipId,
            @AuthenticationPrincipal AuthMember authmember
    ) {
        return ResponseEntity.ok(ApiResponse.of(subscribeService.saveSubscribe(authmember, membershipId)));
    }

    @Member
    @GetMapping
    public ResponseEntity<ApiResponse<List<SubscribeResponseDto>>> findAllSubscribes(
            @AuthenticationPrincipal AuthMember authMember
    ) {
        List<SubscribeResponseDto> responses = subscribeService.findAllSubscribes(authMember);

        return ResponseEntity.ok(ApiResponse.of(responses));
    }

    @Member
    @DeleteMapping("/{subscribeId}")
    public ResponseEntity<ApiResponse<Long>> deleteSubscribe(
            @PathVariable("subscribeId") Long subscribeId,
            @AuthenticationPrincipal AuthMember member
    ) {
        return ResponseEntity.ok(ApiResponse.of(subscribeService.deleteSubscribe(member.getId(), subscribeId)));
    }
}
