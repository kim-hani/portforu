package org.pinggu.portforu.domain.portfolio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "포트폴리오 조회 API", description = "포트폴리오 목록·상세 조회")
@RestController
@RequestMapping("/api/v1/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @Operation(summary = "포트폴리오 목록 조회", description = "포트폴리오 리스트를 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PortfolioListResponseDto>>> findAllPortfolios(
            @ModelAttribute Pagecond pagecond
    ) {
        Page<PortfolioListResponseDto> page = portfolioService.findAllPortfolios(pagecond);
        PageInfo info = PageInfo.builder()
                .pageNum(pagecond.getPageNum())
                .pageSize(pagecond.getPageSize())
                .totalElement(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .build();
        return ResponseEntity.ok(ApiResponse.of(page.getContent(), info));
    }

    @Operation(summary = "포트폴리오 상세 조회", description = "특정 포트폴리오의 상세 정보를 조회합니다.")
    @GetMapping("/{portfolioId}")
    public ResponseEntity<ApiResponse<PortfolioResponseDto>> findPortfolio(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable Long portfolioId
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                portfolioService.findPortfolio(authMember, portfolioId)
        ));
    }
}


