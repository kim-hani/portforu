package org.pinggu.portforu.domain.jobposting.controller;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.annotation.Admin;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.domain.jobposting.dto.request.JobPostingSaveRequestDto;
import org.pinggu.portforu.domain.jobposting.dto.request.JobPostingUpdateRequestDto;
import org.pinggu.portforu.domain.jobposting.dto.response.JobPostingResponseDto;
import org.pinggu.portforu.domain.jobposting.dto.response.JobPostingUpdateResponseDto;
import org.pinggu.portforu.domain.jobposting.service.JobPostingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/job-postings")
@RequiredArgsConstructor
public class JobPostingAdminController {

    private final JobPostingService jobPostingService;

    @Admin
    @PostMapping
    public ResponseEntity<ApiResponse<JobPostingResponseDto>> saveJobPosting(
            @RequestBody JobPostingSaveRequestDto request
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(jobPostingService.saveJobPosting(request)));
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
    public ResponseEntity<ApiResponse<Long>> deleteJobPosting(
            @PathVariable("jobPostingId") Long jobPostingId
    ) {
        return ResponseEntity.ok(ApiResponse.of(jobPostingService.deleteJobPosting(jobPostingId)));
    }

}
