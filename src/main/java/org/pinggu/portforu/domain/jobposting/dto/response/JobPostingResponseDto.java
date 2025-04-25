package org.pinggu.portforu.domain.jobposting.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pinggu.portforu.domain.jobposting.entity.JobPosting;

import java.time.Instant;
import java.time.ZonedDateTime;

@Getter
@Builder
public class JobPostingResponseDto {

    private Long id;

    private String title;

    private String company;

    private String location;

    private String link;

    private String salary;

    private String duty;

    private String employmentType;

    private String educationLevel;

    private String experienceYears;

    private String keyAbilities;

    private Integer minExperienceYears;

    private Integer maxExperienceYears;

    private ZonedDateTime hiringStartAt;

    private ZonedDateTime hiringEndAt;

    private String skills;

    private Instant createdAt;

    private Instant updatedAt;

    private Boolean isDeleted;

    public static JobPostingResponseDto from(JobPosting jp) {
        return JobPostingResponseDto.builder()
                .id(jp.getId())
                .title(jp.getTitle())
                .company(jp.getCompany())
                .location(jp.getLocation())
                .link(jp.getLink())
                .salary(jp.getSalary())
                .duty(jp.getDuty())
                .employmentType(jp.getEmploymentType())
                .educationLevel(jp.getEducationLevel())
                .experienceYears(jp.getExperienceYears())
                .keyAbilities(jp.getKeyAbilities())
                .minExperienceYears(jp.getMinExperienceYears())
                .maxExperienceYears(jp.getMaxExperienceYears())
                .hiringStartAt(jp.getHiringStartAt())
                .hiringEndAt(jp.getHiringEndAt())
                .skills(jp.getSkills())
                .createdAt(jp.getCreatedAt())
                .updatedAt(jp.getUpdatedAt())
                .isDeleted(jp.getIsDeleted())
                .build();
    }

}
