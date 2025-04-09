package org.pinggu.portforu.domain.subscribe.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pinggu.portforu.common.domain.BaseEntity;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.membership.entity.Membership;

import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "subscribes")
public class Subscribe extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id", nullable = false)
    private Membership membership;

    @Column(nullable = false)
    private Instant startDate;

    @Column(nullable = false)
    private Instant endDate;

    @Builder
    public Subscribe(Member member, Membership membership, Instant startDate, Instant endDate) {
        this.member = member;
        this.membership = membership;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Subscribe update(Membership membership, Instant startDate, Instant endDate) {
        return Subscribe.builder()
                .member(this.member)
                .membership(membership)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    public boolean isActive() {
        return Instant.now().isBefore(this.endDate);
    }

}