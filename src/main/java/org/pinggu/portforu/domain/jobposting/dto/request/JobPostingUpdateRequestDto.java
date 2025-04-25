package org.pinggu.portforu.domain.jobposting.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class JobPostingUpdateRequestDto {

    private String title;

    private String company;

    private String location;

    private String salary;

    private String duty;

    private String employmentType;

    private String educationLevel;

    private String experienceYears;

    private String keyAbilities;

    private Integer minExperienceYears;

    private Integer maxExperienceYears;

    private ZonedDateTime closingDate;

    private String skills;
}
