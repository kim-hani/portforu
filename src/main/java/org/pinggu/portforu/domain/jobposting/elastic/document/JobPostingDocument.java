package org.pinggu.portforu.domain.jobposting.elastic.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.pinggu.portforu.domain.jobposting.entity.JobPosting;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPostingDocument {

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
    private Instant hiringStartAt;
    private Instant hiringEndAt;
    private List<String> skills;

    public static JobPostingDocument from(JobPosting jobPosting) {
        return JobPostingDocument.builder()
                .id(jobPosting.getId())
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
                .skills(Arrays.stream(jobPosting.getSkills().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList()))
                .build();
    }

}