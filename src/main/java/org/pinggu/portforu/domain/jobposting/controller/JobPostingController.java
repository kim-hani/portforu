package org.pinggu.portforu.domain.jobposting.controller;

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

@RestController
@RequestMapping("/api/v1/job-postings")
@RequiredArgsConstructor
public class JobPostingController {

    private final JobPostingService jobPostingService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<JobPostingResponseDto>>> findJobPostings(
            @ModelAttribute Pagecond pagecond
    ) {
        Page<JobPostingResponseDto> responses = jobPostingService.findJobPostings(pagecond);
        PageInfo pageInfo = PageInfo.builder()
                .pageNum(pagecond.getPageNum())
                .pageSize(pagecond.getPageSize())
                .totalElement(responses.getTotalElements())
                .totalPage(responses.getTotalPages())
                .build();

        return ResponseEntity.ok().body(ApiResponse.of(responses.getContent(), pageInfo));
    }

    @GetMapping("/{jobPostingId}")
    public ResponseEntity<ApiResponse<JobPostingResponseDto>> findJobPosting(
            @PathVariable("jobPostingId") Long jobPostingId
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(jobPostingService.findJobPosting(jobPostingId)));
    }

}
