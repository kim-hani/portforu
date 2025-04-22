package org.pinggu.portforu.domain.portfolio.controller;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.domain.PageInfo;
import org.pinggu.portforu.common.domain.Pagecond;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.domain.portfolio.dto.response.PortfolioListResponseDto;
import org.pinggu.portforu.domain.portfolio.dto.response.PortfolioResponseDto;
import org.pinggu.portforu.domain.portfolio.service.PortfolioService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PortfolioListResponseDto>>> findAllPortfolios(
            @ModelAttribute Pagecond pagecond
    ) {
        Page<PortfolioListResponseDto> portfolioList = portfolioService.findAllPortfolios(pagecond);
        PageInfo pageInfo = PageInfo.builder()
                .pageNum(pagecond.getPageNum())
                .pageSize(pagecond.getPageSize())
                .totalElement(portfolioList.getTotalElements())
                .totalPage(portfolioList.getTotalPages())
                .build();

        return ResponseEntity.ok().body(ApiResponse.of(portfolioList.getContent(), pageInfo));
    }

    @GetMapping("/{portfolioId}")
    public ResponseEntity<ApiResponse<PortfolioResponseDto>> findPortfolio(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable("portfolioId") Long portfolioId
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(portfolioService.findPortfolio(authMember, portfolioId)));
    }

}

