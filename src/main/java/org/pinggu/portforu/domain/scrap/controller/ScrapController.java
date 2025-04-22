package org.pinggu.portforu.domain.scrap.controller;

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

@RestController
@RequestMapping("/api/v1/scraps")
@RequiredArgsConstructor
public class ScrapController {

    private final ScrapService scrapService;

    @Member
    @PostMapping("/job-postings/{jobPostingId}")
    public ResponseEntity<ApiResponse<ScrapResponseDto>> toggleScrap(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable("jobPostingId") Long jobPostingId
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(scrapService.toggleScrap(authMember, jobPostingId)));
    }

    @Member
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<List<ScrapDetailResponseDto>>> findAllScraps(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable("memberId") Long memberId,
            @ModelAttribute Pagecond pagecond
    ) {
        Page<ScrapDetailResponseDto> responses = scrapService.findAllScraps(authMember, memberId, pagecond);
        PageInfo pageInfo = PageInfo.builder()
                .pageNum(pagecond.getPageNum())
                .pageSize(pagecond.getPageSize())
                .totalElement(responses.getTotalElements())
                .totalPage(responses.getTotalPages())
                .build();

        return ResponseEntity.ok().body(ApiResponse.of(responses.getContent(), pageInfo));
    }

}
