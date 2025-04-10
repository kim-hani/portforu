package org.pinggu.portforu.domain.subscribe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.annotation.Member;
import org.pinggu.portforu.common.domain.PageInfo;
import org.pinggu.portforu.common.domain.Pagecond;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.domain.subscribe.dto.request.SubscribeRequestDto;
import org.pinggu.portforu.domain.subscribe.dto.response.SubscribeResponseDto;
import org.pinggu.portforu.domain.subscribe.service.SubscribeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            @Valid @RequestBody SubscribeRequestDto requestDto,
            @AuthenticationPrincipal AuthMember member
    ) {
        Long memberId = member.getId();
        SubscribeResponseDto dto = subscribeService.saveSubscribe(memberId, membershipId, requestDto);
        return ResponseEntity.ok(ApiResponse.of(dto));
    }

    @Member
    @GetMapping
    public ResponseEntity<ApiResponse<List<SubscribeResponseDto>>> findSubscribes(
            @ModelAttribute Pagecond pagecond,
            @AuthenticationPrincipal AuthMember member
    ) {
        Long memberId = member.getId();
        Pageable pageable = PageRequest.of(pagecond.getPageNum() - 1, pagecond.getPageSize());

        Page<SubscribeResponseDto> responses = subscribeService.findSubscribes(memberId, pageable);

        PageInfo pageInfo = PageInfo.builder()
                .pageNum(pagecond.getPageNum())
                .pageSize(pagecond.getPageSize())
                .totalElement(responses.getTotalElements())
                .totalPage(responses.getTotalPages())
                .build();

        return ResponseEntity.ok(ApiResponse.of(responses.getContent(), pageInfo));
    }

    @Member
    @DeleteMapping("/{subscribeId}")
    public ResponseEntity<ApiResponse<Long>> deleteSubscribe(
            @PathVariable("subscribeId") Long subscribeId,
            @AuthenticationPrincipal AuthMember member
    ) {
        Long deletedSubscribeId = subscribeService.deleteSubscribe(member.getId(), subscribeId);

        return ResponseEntity.ok(ApiResponse.of(deletedSubscribeId));
    }
}
