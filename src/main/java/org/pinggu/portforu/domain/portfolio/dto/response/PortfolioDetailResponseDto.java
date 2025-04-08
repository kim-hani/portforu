package org.pinggu.portforu.domain.portfolio.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pinggu.portforu.domain.comment.dto.response.CommentResponseDto;
import org.pinggu.portforu.domain.portfolio.entity.Portfolio;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PortfolioDetailResponseDto {

    private final Long id;
    private final Long memberId;
    private final String title;
    private final String description;
    private final String fileUrl;
    private final Integer views;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;
    private final List<CommentResponseDto> comments;

    public static PortfolioDetailResponseDto from(Portfolio portfolio, List<CommentResponseDto> comments) {
        return PortfolioDetailResponseDto.builder()
                .id(portfolio.getId())
                .memberId(portfolio.getMember().getId())
                .title(portfolio.getTitle())
                .description(portfolio.getDescription())
                .fileUrl(portfolio.getFileUrl())
                .views(portfolio.getViews())
                .createdAt(portfolio.getCreatedAt())
                .updatedAt(portfolio.getUpdatedAt())
                .deletedAt(portfolio.getDeletedAt())
                .comments(comments)
                .build();
    }
}

