package org.pinggu.portforu.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pinggu.portforu.domain.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponseDto {
    private final Long id;
    private final Long memberId;
    private final Long portfolioId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;

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
