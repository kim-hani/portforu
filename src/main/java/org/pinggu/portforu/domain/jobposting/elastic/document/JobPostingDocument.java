package org.pinggu.portforu.domain.jobposting.elastic.document;

import jakarta.persistence.Id;
import lombok.*;
import org.pinggu.portforu.domain.jobposting.entity.JobPosting;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.Instant;

@Document(indexName = "job_postings")
@Builder
public class JobPostingDocument {

    @Id
    private String id;

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
    private Instant hiringStartAt;
    private Instant hiringEndAt;
    private String skills;

    public static JobPostingDocument from(JobPosting jobPosting) {
        return JobPostingDocument.builder()
                .id(String.valueOf(jobPosting.getId()))
                .title(jobPosting.getTitle())
                .company(jobPosting.getCompany())
                .location(jobPosting.getLocation())
                .link(jobPosting.getLink())
                .salary(jobPosting.getSalary())
                .duty(jobPosting.getDuty())
                .employmentType(jobPosting.getEmploymentType())
                .educationLevel(jobPosting.getEducationLevel())
                .experienceYears(jobPosting.getExperienceYears())
                .keyAbilities(jobPosting.getKeyAbilities())
                .minExperienceYears(jobPosting.getMinExperienceYears())
                .maxExperienceYears(jobPosting.getMaxExperienceYears())
                .hiringStartAt(jobPosting.getHiringStartAt() != null ? jobPosting.getHiringStartAt().toInstant() : null)
                .hiringEndAt(jobPosting.getHiringEndAt() != null ? jobPosting.getHiringEndAt().toInstant() : null)
                .skills(jobPosting.getSkills())
                .build();
    }
}