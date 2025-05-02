package org.pinggu.portforu.domain.jobposting.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.annotation.Admin;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.domain.jobposting.dto.request.JobPostingSaveRequestDto;
import org.pinggu.portforu.domain.jobposting.dto.request.JobPostingUpdateRequestDto;
import org.pinggu.portforu.domain.jobposting.dto.response.JobPostingResponseDto;
import org.pinggu.portforu.domain.jobposting.service.JobPostingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "채용공고 관리 API", description = "관리자가 채용공고를 생성·수정·삭제합니다.")
@RestController
@RequestMapping("/api/v1/admin/job-postings")
@RequiredArgsConstructor
public class JobPostingAdminController {

    private final JobPostingService jobPostingService;

    @Operation(summary = "채용공고 생성", description = "새 채용공고를 등록합니다.")
    @Admin
    @PostMapping
    public ResponseEntity<ApiResponse<JobPostingResponseDto>> saveJobPosting(
            @RequestBody JobPostingSaveRequestDto requestDto
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                jobPostingService.saveJobPosting(requestDto)
        ));
    }

    @Operation(summary = "채용공고 수정", description = "기존 채용공고를 수정합니다.")
    @Admin
    @PutMapping("/{jobPostingId}")
    public ResponseEntity<ApiResponse<String>> updateJobPosting(
            @PathVariable Long jobPostingId,
            @RequestBody JobPostingUpdateRequestDto requestDto
    ) {
        jobPostingService.updateJobPosting(jobPostingId, requestDto);
        return ResponseEntity.ok(ApiResponse.of("채용공고 수정이 완료되었습니다."));
    }

    @Operation(summary = "채용공고 삭제", description = "기존 채용공고를 삭제합니다.")
    @Admin
    @DeleteMapping("/{jobPostingId}")
    public ResponseEntity<ApiResponse<Long>> deleteJobPosting(
            @PathVariable Long jobPostingId
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                jobPostingService.deleteJobPosting(jobPostingId)
        ));
    }
}
