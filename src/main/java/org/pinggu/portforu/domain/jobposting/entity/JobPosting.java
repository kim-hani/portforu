package org.pinggu.portforu.domain.jobposting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.pinggu.portforu.common.domain.BaseEntity;

import java.time.ZonedDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "job_postings")
@SQLDelete(sql = "UPDATE job_postings SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class JobPosting extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false, unique = true)
    private String link;

    @Column(nullable = false)
    private String salary;

    @Column(nullable = false)
    private String duty;

    @Column(nullable = false)
    private String employmentType;

    @Column(nullable = false)
    private String educationLevel;

    @Column(nullable = false)
    private String experienceYears;

    @Column(nullable = false)
    private String keyAbilities;

    @Column
    private Integer minExperienceYears;

    @Column
    private Integer maxExperienceYears;

    @Column
    private ZonedDateTime hiringStartAt;

    @Column
    private ZonedDateTime hiringEndAt;

    @Column
    private String skills;

    @Column(nullable = false)
    private boolean indexed = false;

    @Builder
    public JobPosting(String title, String company, String location, String link,
                      String salary, String duty, String employmentType,
                      String educationLevel, String experienceYears, String keyAbilities,
                      Integer minExperienceYears, Integer maxExperienceYears,
                      ZonedDateTime hiringStartAt, ZonedDateTime hiringEndAt,
                      String skills) {
        this.title             = title;
        this.company           = company;
        this.location          = location;
        this.link              = link;
        this.salary            = salary;
        this.duty              = duty;
        this.employmentType    = employmentType;
        this.educationLevel    = educationLevel;
        this.experienceYears   = experienceYears;
        this.keyAbilities      = keyAbilities;
        this.minExperienceYears= minExperienceYears;
        this.maxExperienceYears= maxExperienceYears;
        this.hiringStartAt     = hiringStartAt;
        this.hiringEndAt       = hiringEndAt;
        this.skills            = skills;
        this.indexed           = false;
    }

    public void update(String title, String company, String location,
                       String salary, String duty, String employmentType,
                       String educationLevel, String experienceYears, String keyAbilities,
                       Integer minExperienceYears, Integer maxExperienceYears,
                       ZonedDateTime closingDate, String skills) {
        if (title != null)
            this.title = title;
        if (company != null)
            this.company = company;
        if (location != null)
            this.location = location;
        if (salary != null)
            this.salary = salary;
        if (duty != null)
            this.duty = duty;
        if (employmentType != null)
            this.employmentType = employmentType;
        if (educationLevel != null)
            this.educationLevel = educationLevel;
        if (experienceYears != null)
            this.experienceYears = experienceYears;
        if (keyAbilities != null)
            this.keyAbilities = keyAbilities;
        if (minExperienceYears != null)
            this.minExperienceYears = minExperienceYears;
        if (maxExperienceYears != null)
            this.maxExperienceYears = maxExperienceYears;
        if (closingDate != null)
            this.hiringEndAt = closingDate;
        if (skills != null)
            this.skills = skills;
    }

    public void markAsIndexed() {
        this.indexed = true;
    }
}

