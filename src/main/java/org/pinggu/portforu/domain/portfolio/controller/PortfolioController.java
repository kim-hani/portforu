package org.pinggu.portforu.domain.portfolio.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.annotation.Member;
import org.pinggu.portforu.common.domain.PageInfo;
import org.pinggu.portforu.common.domain.Pagecond;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.domain.portfolio.dto.request.PortfolioRequestDto;
import org.pinggu.portforu.domain.portfolio.dto.request.PortfolioUpdateRequestDto;
import org.pinggu.portforu.domain.portfolio.dto.response.PortfolioDetailResponseDto;
import org.pinggu.portforu.domain.portfolio.dto.response.PortfolioResponseDto;
import org.pinggu.portforu.domain.portfolio.service.PortfolioService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @Member
    @PostMapping
    public ResponseEntity<ApiResponse<PortfolioResponseDto>> savePortfolio(
            @AuthenticationPrincipal AuthMember authMember,
            @Valid @RequestBody PortfolioRequestDto requestDto
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(
                portfolioService.savePortfolio(requestDto, authMember.getId())));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> findAllPortfolios(
            @ModelAttribute Pagecond pagecond
    ) {
        Page<PortfolioResponseDto> portfolios = portfolioService.findAllPortfolios(pagecond);

        PageInfo pageInfo = PageInfo.builder()
                .pageNum(pagecond.getPageNum())
                .pageSize(pagecond.getPageSize())
                .totalElement(portfolios.getTotalElements())
                .totalPage(portfolios.getTotalPages())
                .build();

        return ResponseEntity.ok().body(ApiResponse.of(portfolios.getContent(), pageInfo));
    }


    @GetMapping("/{portfolioId}")
    public ResponseEntity<ApiResponse<PortfolioDetailResponseDto>> findPortfolio(
            @PathVariable("portfolioId") Long portfolioId
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(
                portfolioService.findPortfolio(portfolioId)));
    }

    @Member
    @PutMapping("/{portfolioId}")
    public ResponseEntity<ApiResponse<PortfolioResponseDto>> updatePortfolio(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable Long portfolioId,
            @Valid @RequestBody PortfolioUpdateRequestDto requestDto
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(
                portfolioService.updatePortfolio(portfolioId, requestDto, authMember.getId())));
    }

    @Member
    @DeleteMapping("/{portfolioId}")
    public ResponseEntity<ApiResponse<Void>> deletePortfolio(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable("portfolioId") Long portfolioId
    ) {
        portfolioService.deletePortfolio(portfolioId, authMember.getId());
        return ResponseEntity.ok(ApiResponse.of(null));
    }
}

