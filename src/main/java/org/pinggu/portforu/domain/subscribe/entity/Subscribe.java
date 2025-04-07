package org.pinggu.portforu.domain.subscribe.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pinggu.portforu.common.domain.BaseEntity;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "subscribes")
public class Subscribe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId; // 사용자 ID

    @Column(nullable = false)
    private Long membershipId; // 멤버십 ID

    @Column(nullable = false)
    private LocalDateTime startDate; // 구독 시작일

    @Column(nullable = false)
    private LocalDateTime endDate; // 구독 종료일

    // 생성자
    @Builder
    public Subscribe(Long memberId, Long membershipId, LocalDateTime startDate, LocalDateTime endDate) {
        this.memberId = memberId;
        this.membershipId = membershipId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Subscribe update(Long membershipId, LocalDateTime startDate, LocalDateTime endDate) {
        return Subscribe.builder()
                .memberId(this.memberId)
                .membershipId(membershipId)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}