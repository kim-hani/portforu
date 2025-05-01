package org.pinggu.portforu.domain.jobposting.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.domain.PageInfo;
import org.pinggu.portforu.common.domain.Pagecond;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.domain.jobposting.dto.response.JobPostingResponseDto;
import org.pinggu.portforu.domain.jobposting.service.JobPostingService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "채용공고 조회 API", description = "채용공고 목록 및 상세 조회")
@RestController
@RequestMapping("/api/v1/job-postings")
@RequiredArgsConstructor
public class JobPostingController {

    private final JobPostingService jobPostingService;

    @Operation(summary = "채용공고 목록 조회", description = "페이지네이션된 채용공고 리스트를 반환합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<JobPostingResponseDto>>> findAllJobPostings(
            @ModelAttribute Pagecond pagecond
    ) {
        Page<JobPostingResponseDto> responses = jobPostingService.findAllJobPostings(pagecond);
        PageInfo pageInfo = PageInfo.builder()
                .pageNum(pagecond.getPageNum())
                .pageSize(pagecond.getPageSize())
                .totalElement(responses.getTotalElements())
                .totalPage(responses.getTotalPages())
                .build();

        return ResponseEntity.ok(ApiResponse.of(responses.getContent(), pageInfo));
    }

    @Operation(summary = "채용공고 상세 조회", description = "단일 채용공고의 상세 정보를 반환합니다.")
    @GetMapping("/{jobPostingId}")
    public ResponseEntity<ApiResponse<JobPostingResponseDto>> findJobPosting(
            @PathVariable Long jobPostingId
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                jobPostingService.findJobPosting(jobPostingId)
        ));
    }
}