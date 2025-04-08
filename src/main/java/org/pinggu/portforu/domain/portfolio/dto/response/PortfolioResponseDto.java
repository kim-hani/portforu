package org.pinggu.portforu.domain.portfolio.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pinggu.portforu.domain.portfolio.entity.Portfolio;

import java.time.LocalDateTime;


@Getter
@Builder
public class PortfolioResponseDto {
    private final Long id;
    private final Long memberId;
    private final String title;
    private final String description;
    private final String fileUrl;
    private final Integer views;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;

    public static PortfolioResponseDto from(Portfolio portfolio) {
        return PortfolioResponseDto.builder()
                .id(portfolio.getId())
                .memberId(portfolio.getMember().getId())
                .title(portfolio.getTitle())
                .description(portfolio.getDescription())
                .fileUrl(portfolio.getFileUrl())
                .views(portfolio.getViews())
                .createdAt(portfolio.getCreatedAt())
                .updatedAt(portfolio.getUpdatedAt())
                .deletedAt(portfolio.getDeletedAt())
                .build();
    }

}