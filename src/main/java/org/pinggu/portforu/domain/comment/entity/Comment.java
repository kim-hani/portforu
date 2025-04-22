package org.pinggu.portforu.domain.comment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.pinggu.portforu.common.domain.BaseEntity;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.portfolio.entity.Portfolio;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
@SQLDelete(sql = "UPDATE comments  SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Comment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @Column(nullable = false)
    private String content;

    @Builder
    public Comment(Member member, Portfolio portfolio, String content) {
        this.member = member;
        this.portfolio = portfolio;
        this.content = content;
    }

    public void update(String content){
        if(content != null && !content.isBlank()){
            this.content = content;
        }
    }
}

