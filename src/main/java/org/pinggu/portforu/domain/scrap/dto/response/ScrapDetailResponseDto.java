package org.pinggu.portforu.domain.scrap.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pinggu.portforu.domain.jobposting.entity.JobPosting;
import org.pinggu.portforu.domain.scrap.entity.Scrap;

import java.time.Instant;
import java.time.ZonedDateTime;

@Getter
@Builder
public class ScrapDetailResponseDto {

    private final Long id;

    private final Long memberId;

    private final Long jobPostingId;

    private final String title;

    private final String company;

    private final String location;

    private final String link;

    private final String salary;

    private final String duty;

    private final String employmentType;

    private final String educationLevel;

    private final String experienceYears;

    private final String keyAbilities;

    private final Integer minExperienceYears;

    private final Integer maxExperienceYears;

    private final ZonedDateTime hiringStartAt;

    private final ZonedDateTime hiringEndAt;

    private final String skills;

    private final Instant createdAt;

    private final Instant updatedAt;

    private final Boolean isDeleted;

    public static ScrapDetailResponseDto from(Scrap scrap) {
        JobPosting jp = scrap.getJobPosting();
        return ScrapDetailResponseDto.builder()
                .id(scrap.getId())
                .memberId(scrap.getMember().getId())
                .jobPostingId(jp.getId())
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
                .createdAt(scrap.getCreatedAt())
                .updatedAt(scrap.getUpdatedAt())
                .isDeleted(scrap.getIsDeleted())
                .build();
    }
}