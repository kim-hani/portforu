package org.pinggu.portforu.domain.subscribe.controller;

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
@RequestMapping("/api/v1")
public class SubscribeController {

    private final SubscribeService subscribeService;

    // 구독 생성
    @Member
    @PostMapping("/memberships/{membershipId}/subscribes")
    public ResponseEntity<ApiResponse<SubscribeResponseDto>> saveSubscribe(
            @PathVariable Long membershipId,
            @RequestBody SubscribeRequestDto requestDto,
            @AuthenticationPrincipal AuthMember member) {
        // PathVariable의 membershipId를 사용하고, 나머지 값은 요청 DTO에서 사용합니다.
        Long memberId = member.getId();
        SubscribeResponseDto dto = subscribeService.saveSubscribe(memberId, membershipId, requestDto);
        return ResponseEntity.ok(ApiResponse.of(dto));
    }

    // 구독 목록 조회 (페이징 적용)
    @Member
    @GetMapping("/my/subscribes")
    public ResponseEntity<ApiResponse<List<SubscribeResponseDto>>> findSubscribes(
            @ModelAttribute Pagecond pagecond,
            @AuthenticationPrincipal AuthMember member) {

        Long memberId = member.getId();
        Pageable pageable = PageRequest.of(pagecond.getPageNum() - 1, pagecond.getPageSize());


        // member.getId()를 통해 인증된 사용자의 ID를 가져옵니다.
        Page<SubscribeResponseDto> responses = subscribeService.findSubscribes(memberId, pageable);

        PageInfo pageInfo = PageInfo.builder()
                .pageNum(pagecond.getPageNum())
                .pageSize(pagecond.getPageSize())
                .totalElement(responses.getTotalElements())
                .totalPage(responses.getTotalPages())
                .build();

        return ResponseEntity.ok(ApiResponse.of(responses.getContent(), pageInfo));
    }


    // 구독 취소
    @Member
    @DeleteMapping("/my/subscribes/{subscribeId}")
    public ResponseEntity<ApiResponse<String>> deleteSubscribe(
            @PathVariable Long subscribeId,
            @AuthenticationPrincipal AuthMember member
    ) {
        subscribeService.deleteSubscribe(member.getId(), subscribeId);
        return ResponseEntity.ok(ApiResponse.of("구독이 취소되었습니다."));
    }
}
