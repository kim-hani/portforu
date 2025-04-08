package org.pinggu.portforu.domain.subscribe.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pinggu.portforu.common.domain.BaseEntity;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.membership.entity.Membership;

import java.time.LocalDateTime;

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
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Builder
    public Subscribe(Member member, Membership membership, LocalDateTime startDate, LocalDateTime endDate) {
        this.member = member;
        this.membership = membership;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Subscribe update(Membership membership, LocalDateTime startDate, LocalDateTime endDate) {
        return Subscribe.builder()
                .member(this.member)
                .membership(membership)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    public boolean isActive() {
        return LocalDateTime.now().isBefore(this.endDate);
    }

}