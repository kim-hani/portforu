package org.pinggu.portforu.domain.jobposting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.common.domain.Pagecond;
import org.pinggu.portforu.domain.jobposting.dto.request.JobPostingSaveRequestDto;
import org.pinggu.portforu.domain.jobposting.dto.request.JobPostingUpdateRequestDto;
import org.pinggu.portforu.domain.jobposting.dto.response.JobPostingResponseDto;
import org.pinggu.portforu.domain.jobposting.elastic.service.JobPostingSearchService;
import org.pinggu.portforu.domain.jobposting.entity.JobPosting;
import org.pinggu.portforu.domain.jobposting.repository.JobPostingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobPostingService {

    private final JobPostingFinder jobPostingFinder;
    private final JobPostingRepository jobPostingRepository;
    private final JobPostingSearchService jobPostingSearchService;

    @Transactional
    public JobPostingResponseDto saveJobPosting(JobPostingSaveRequestDto requestDto) {
        System.out.println("저장되고있습니다");
        JobPosting jobPosting = JobPosting.builder()
                .title(requestDto.getTitle())
                .company(requestDto.getCompany())
                .location(requestDto.getLocation())
                .link(requestDto.getLink())
                .salary(requestDto.getSalary())
                .duty(requestDto.getDuty())
                .employmentType(requestDto.getEmploymentType())
                .educationLevel(requestDto.getEducationLevel())
                .experienceYears(requestDto.getExperienceYears())
                .keyAbilities(requestDto.getKeyAbilities())
                .minExperienceYears(requestDto.getMinExperienceYears())
                .maxExperienceYears(requestDto.getMaxExperienceYears())
                .hiringStartAt(requestDto.getHiringStartAt())
                .hiringEndAt(requestDto.getHiringEndAt())
                .skills(requestDto.getSkills())
                .build();

        if (jobPostingRepository.findByLink(jobPosting.getLink()).isEmpty()) {
            jobPostingRepository.save(jobPosting);
            jobPostingSearchService.index(jobPosting);
        }

        return JobPostingResponseDto.from(jobPosting);
    }

    @Transactional(readOnly = true)
    public Page<JobPostingResponseDto> findAllJobPostings(Pagecond pagecond) {
        Pageable pageable = PageRequest.of(pagecond.getPageNum() - 1, pagecond.getPageSize(), Sort.by(Sort.Order.desc("id")));
        Page<JobPosting> jobPostings = jobPostingRepository.findAll(pageable);

        return jobPostings.map(JobPostingResponseDto::from);
    }

    @Transactional(readOnly = true)
    public JobPostingResponseDto findJobPosting(Long jobPostingId) {
        JobPosting jobPosting = jobPostingFinder.findJobPostingById(jobPostingId);
        return JobPostingResponseDto.from(jobPosting);
    }

    @Transactional
    public void updateJobPosting(Long jobPostingId, JobPostingUpdateRequestDto requestDto) {
        JobPosting jobPosting = jobPostingFinder.findJobPostingById(jobPostingId);

        jobPosting.update(requestDto.getTitle(), requestDto.getCompany(), requestDto.getLocation(),
                requestDto.getSalary(), requestDto.getDuty(), requestDto.getEmploymentType(),
                requestDto.getEducationLevel(), requestDto.getExperienceYears(), requestDto.getKeyAbilities(),
                requestDto.getMinExperienceYears(), requestDto.getMaxExperienceYears(),
                requestDto.getClosingDate(), requestDto.getSkills());

        jobPostingSearchService.index(jobPosting);
    }

    @Transactional
    public Long deleteJobPosting(Long jobPostingId) {
        JobPosting jobPosting = jobPostingFinder.findJobPostingById(jobPostingId);
        jobPostingRepository.delete(jobPosting);
        jobPostingSearchService.deleteById(jobPosting.getId().toString());
        return jobPosting.getId();
    }

    @Transactional(readOnly = true)
    public List<JobPosting> findLatest5Postings() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.desc("createdAt")));
        return jobPostingRepository.findAll(pageable).getContent();
    }

}
