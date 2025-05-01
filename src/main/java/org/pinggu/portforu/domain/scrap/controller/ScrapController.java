package org.pinggu.portforu.domain.scrap.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.annotation.Member;
import org.pinggu.portforu.common.domain.PageInfo;
import org.pinggu.portforu.common.domain.Pagecond;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.domain.scrap.dto.response.ScrapDetailResponseDto;
import org.pinggu.portforu.domain.scrap.dto.response.ScrapResponseDto;
import org.pinggu.portforu.domain.scrap.service.ScrapService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "스크랩 API", description = "채용공고 스크랩 토글 및 조회")
@RestController
@RequestMapping("/api/v1/scraps")
@RequiredArgsConstructor
public class ScrapController {

    private final ScrapService scrapService;

    @Operation(summary = "스냅 토글", description = "회원이 특정 채용공고를 스크랩하거나 해제합니다.")
    @Member
    @PostMapping("/job-postings/{jobPostingId}")
    public ResponseEntity<ApiResponse<ScrapResponseDto>> toggleScrap(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable Long jobPostingId
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                scrapService.toggleScrap(authMember, jobPostingId)
        ));
    }

    @Operation(summary = "스크랩 목록 조회", description = "회원의 스크랩한 공고 리스트를 조회합니다.")
    @Member
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<List<ScrapDetailResponseDto>>> findAllScraps(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable Long memberId,
            @ModelAttribute Pagecond pagecond
    ) {
        Page<ScrapDetailResponseDto> page = scrapService.findAllScraps(authMember, memberId, pagecond);
        PageInfo info = PageInfo.builder()
                .pageNum(pagecond.getPageNum())
                .pageSize(pagecond.getPageSize())
                .totalElement(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .build();
        return ResponseEntity.ok(ApiResponse.of(page.getContent(), info));
    }
}

