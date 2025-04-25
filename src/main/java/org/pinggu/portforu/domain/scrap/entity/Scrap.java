package org.pinggu.portforu.domain.scrap.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.pinggu.portforu.common.domain.BaseEntity;
import org.pinggu.portforu.domain.jobposting.entity.JobPosting;
import org.pinggu.portforu.domain.member.entity.Member;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "scraps")
@SQLDelete(sql = "UPDATE scraps SET is_deleted = true WHERE id = ?")
public class Scrap extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;

    @Builder
    public Scrap(Member member, JobPosting jobPosting) {
        this.member = member;
        this.jobPosting = jobPosting;
    }

}
