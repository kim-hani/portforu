package org.pinggu.portforu.domain.jobposting.controller;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.annotation.Admin;
import org.pinggu.portforu.common.domain.PageInfo;
import org.pinggu.portforu.common.domain.Pagecond;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.domain.jobposting.dto.request.JobPostingSaveRequestDto;
import org.pinggu.portforu.domain.jobposting.dto.request.JobPostingUpdateRequestDto;
import org.pinggu.portforu.domain.jobposting.dto.response.JobPostingResponseDto;
import org.pinggu.portforu.domain.jobposting.dto.response.JobPostingUpdateResponseDto;
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

    // scrap 기능 테스트 하기 위해 작성
    @Admin
    @PostMapping
    public ResponseEntity<ApiResponse<JobPostingResponseDto>> saveJobPosting(
            @RequestBody JobPostingSaveRequestDto request
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(jobPostingService.saveJobPosting(request)));
    }

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

    @Admin
    @PutMapping("/{jobPostingId}")
    public ResponseEntity<ApiResponse<JobPostingUpdateResponseDto>> updateJobPosting(
            @PathVariable("jobPostingId") Long jobPostingId,
            @RequestBody JobPostingUpdateRequestDto request
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(jobPostingService.updateJobPosting(jobPostingId, request)));
    }

    @Admin
    @DeleteMapping("/{jobPostingId}")
    public ResponseEntity<ApiResponse<Void>> deleteJobPosting(
            @PathVariable("jobPostingId") Long jobPostingId
    ) {
        jobPostingService.deleteJobPosting(jobPostingId);

        return ResponseEntity.noContent().build();
    }

}
