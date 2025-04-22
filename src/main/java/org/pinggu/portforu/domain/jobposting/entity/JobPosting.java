package org.pinggu.portforu.domain.jobposting.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.pinggu.portforu.common.domain.BaseEntity;

import java.time.Instant;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "job_postings")
@SQLDelete(sql = "UPDATE job_postings SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class JobPosting extends BaseEntity {

    private String name;

    private String industry;

    private String address;

    private String salary;

    private String qualifications;

    private String preferential;

    private Instant postingDate;

    private Instant closingDate;

    @Builder
    public JobPosting(String name, String industry, String address, String salary, String qualifications,
                      String preferential, Instant postingDate, Instant closingDate) {
        this.name = name;
        this.industry = industry;
        this.address = address;
        this.salary = salary;
        this.qualifications = qualifications;
        this.preferential = preferential;
        this.postingDate = postingDate;
        this.closingDate = closingDate;
    }

    public void update(String name, String industry, String address, String salary, String qualifications,
                       String preferential, Instant closingDate) {
        if (name != null) this.name = name;
        if (industry != null) this.industry = industry;
        if (address != null) this.address = address;
        if (salary != null) this.salary = salary;
        if (qualifications != null) this.qualifications = qualifications;
        if (preferential != null) this.preferential = preferential;
        if (closingDate != null) this.closingDate = closingDate;
    }

}
