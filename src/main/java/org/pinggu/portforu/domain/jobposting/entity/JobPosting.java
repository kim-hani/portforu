package org.pinggu.portforu.domain.jobposting.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pinggu.portforu.common.domain.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "job_postings")
public class JobPosting extends BaseEntity {

    private String name;

    private String industry;

    private String address;

    private String salary;

    private String qualifications;

    private String preferential;

    private String postingDate;

    private String closingDate;

    @Builder
    public JobPosting(String name, String industry, String address, String salary, String qualifications,
                      String preferential, String postingDate, String closingDate) {
        this.name = name;
        this.industry = industry;
        this.address = address;
        this.salary = salary;
        this.qualifications = qualifications;
        this.preferential = preferential;
        this.postingDate = postingDate;
        this.closingDate = closingDate;
    }

}
