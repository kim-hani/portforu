package org.pinggu.portforu.domain.jobposting.elastic.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.domain.jobposting.elastic.document.JobPostingDocument;
import org.pinggu.portforu.domain.jobposting.elastic.service.JobPostingSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "채용공고 키워드 조회 API", description = "키워드로 엘라스틱서치 적용된 채용공고 조회")
@RestController
@RequestMapping("/api/v1/job-postings/search")
@RequiredArgsConstructor
public class JobPostingSearchController {

    private final JobPostingSearchService searchService;

    @Operation(summary = "키워드로 채용공고 목록 조회", description = "키워드를 포함하는 페이지네이션된 채용공고 리스트를 반환합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<JobPostingDocument>>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ApiResponse.of(searchService.search(keyword, page, size)));
    }

}
