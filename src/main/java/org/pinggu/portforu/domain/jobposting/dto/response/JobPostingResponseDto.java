package org.pinggu.portforu.domain.jobposting.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pinggu.portforu.domain.jobposting.entity.JobPosting;

import java.time.Instant;

@Getter
@Builder
public class JobPostingResponseDto {

    private final Long id;
    private final String name;
    private final String industry;
    private final String address;
    private final String salary;
    private final String qualifications;
    private final String preferential;
    private final String postingDate;
    private final String closingDate;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final Instant deletedAt;

    public static JobPostingResponseDto from(JobPosting jobPosting) {
        return JobPostingResponseDto.builder()
                .id(jobPosting.getId())
                .name(jobPosting.getName())
                .industry(jobPosting.getIndustry())
                .address(jobPosting.getAddress())
                .salary(jobPosting.getSalary())
                .qualifications(jobPosting.getQualifications())
                .preferential(jobPosting.getPreferential())
                .postingDate(jobPosting.getPostingDate())
                .closingDate(jobPosting.getClosingDate())
                .createdAt(jobPosting.getCreatedAt())
                .updatedAt(jobPosting.getUpdatedAt())
                .deletedAt(jobPosting.getDeletedAt())
                .build();
    }

}
