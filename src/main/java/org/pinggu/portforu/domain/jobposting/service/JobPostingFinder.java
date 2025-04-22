package org.pinggu.portforu.domain.jobposting.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.jobposting.entity.JobPosting;
import org.pinggu.portforu.domain.jobposting.repository.JobPostingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPostingFinder {

    private final JobPostingRepository jobPostingRepository;

    public JobPosting findJobPostingById(Long id) {
        return jobPostingRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "채용 공고가 존재하지 않습니다."));
    }

}
