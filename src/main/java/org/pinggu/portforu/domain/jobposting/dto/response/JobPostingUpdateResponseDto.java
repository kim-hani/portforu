package org.pinggu.portforu.domain.jobposting.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pinggu.portforu.domain.jobposting.entity.JobPosting;

import java.time.LocalDateTime;

@Getter
@Builder
public class JobPostingUpdateResponseDto {

    private final Long id;
    private final String name;
    private final String industry;
    private final String address;
    private final String salary;
    private final String qualifications;
    private final String preferential;
    private final String postingDate;
    private final String closingDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;

    public static JobPostingUpdateResponseDto from(JobPosting jobPosting) {
        return JobPostingUpdateResponseDto.builder()
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
                .updatedAt(LocalDateTime.now())
                .deletedAt(jobPosting.getDeletedAt())
                .build();
    }

}
