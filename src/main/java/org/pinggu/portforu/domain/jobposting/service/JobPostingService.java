package org.pinggu.portforu.domain.jobposting.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.domain.Pagecond;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.jobposting.dto.request.JobPostingSaveRequestDto;
import org.pinggu.portforu.domain.jobposting.dto.request.JobPostingUpdateRequestDto;
import org.pinggu.portforu.domain.jobposting.dto.response.JobPostingResponseDto;
import org.pinggu.portforu.domain.jobposting.dto.response.JobPostingUpdateResponseDto;
import org.pinggu.portforu.domain.jobposting.entity.JobPosting;
import org.pinggu.portforu.domain.jobposting.repository.JobPostingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;

    @Transactional
    public JobPostingResponseDto saveJobPosting(JobPostingSaveRequestDto request) {
        JobPosting jobPosting = new JobPosting(request.getName(), request.getIndustry(), request.getAddress(), request.getSalary(),
                request.getQualifications(), request.getPreferential(), request.getPostingDate(), request.getClosingDate());

        jobPostingRepository.save(jobPosting);

        return JobPostingResponseDto.from(jobPosting);
    }

    @Transactional(readOnly = true)
    public Page<JobPostingResponseDto> findJobPostings(Pagecond pagecond) {
        Pageable pageable = PageRequest.of(pagecond.getPageNum() - 1, pagecond.getPageSize(), Sort.by(Sort.Order.desc("createdAt")));
        Page<JobPosting> jobPostings = jobPostingRepository.findAllByDeletedAtIsNull(pageable);

        return jobPostings.map(JobPostingResponseDto::from);
    }

    @Transactional(readOnly = true)
    public JobPostingResponseDto findJobPosting(Long jobPostingId) {
        JobPosting jobPosting = findJobPostingById(jobPostingId);

        return JobPostingResponseDto.from(jobPosting);
    }

    @Transactional
    public JobPostingUpdateResponseDto updateJobPosting(Long jobPostingId, JobPostingUpdateRequestDto request) {
        JobPosting jobPosting = findJobPostingById(jobPostingId);

        jobPosting.update(request.getName(), request.getIndustry(), request.getAddress(), request.getSalary(),
                request.getQualifications(), request.getPreferential(), request.getClosingDate());

        return JobPostingUpdateResponseDto.from(jobPosting);
    }

    @Transactional
    public void deleteJobPosting(Long jobPostingId) {
        JobPosting jobPosting = findJobPostingById(jobPostingId);

        jobPosting.delete();
    }

    public JobPosting findJobPostingById(Long id) {
        return jobPostingRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "채용 공고가 존재하지 않습니다."));
    }

}
