package org.pinggu.portforu.domain.jobposting.elastic.controller;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.domain.jobposting.elastic.document.JobPostingDocument;
import org.pinggu.portforu.domain.jobposting.elastic.service.JobPostingSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/job-postings/search")
@RequiredArgsConstructor
public class JobPostingSearchController {

    private final JobPostingSearchService searchService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<JobPostingDocument>>> search(
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(ApiResponse.of(searchService.search(keyword)));
    }

}
