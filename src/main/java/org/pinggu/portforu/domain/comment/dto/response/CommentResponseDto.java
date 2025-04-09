package org.pinggu.portforu.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pinggu.portforu.domain.comment.entity.Comment;

import java.time.Instant;

@Getter
@Builder
public class CommentResponseDto {
    private final Long id;
    private final Long memberId;
    private final Long portfolioId;
    private final String content;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final Instant deletedAt;

    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .memberId(comment.getMember().getId())
                .portfolioId(comment.getPortfolio().getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .deletedAt(comment.getDeletedAt())
                .build();
    }
}
