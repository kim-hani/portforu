package org.pinggu.portforu.domain.portfolio.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pinggu.portforu.domain.portfolio.entity.Portfolio;

@Getter
@Builder
public class PortfolioListResponseDto {

    private final Long id;
    private final String title;
    private final Integer views;

    public static PortfolioListResponseDto from(Portfolio portfolio) {
        return PortfolioListResponseDto.builder()
                .id(portfolio.getId())
                .title(portfolio.getTitle())
                .views(portfolio.getViews())
                .build();
    }

}
