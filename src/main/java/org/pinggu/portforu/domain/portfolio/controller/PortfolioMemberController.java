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
public class PortfolioMemberController {

    private final PortfolioService portfolioService;

    @Member
    @PostMapping
    public ResponseEntity<ApiResponse<PortfolioResponseDto>> savePortfolio(
            @AuthenticationPrincipal AuthMember authMember,
            @Valid @RequestBody PortfolioRequestDto requestDto
    ){
        return ResponseEntity.ok(ApiResponse.of(portfolioService.savePortfolio(authMember, requestDto)));
    }

    @Member
    @GetMapping("/members/{memberId}")
    public ResponseEntity<ApiResponse<List<PortfolioListResponseDto>>> findMyAllPortfolios(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable("memberId") Long memberId,
            @ModelAttribute Pagecond pagecond
    ){
        Page<PortfolioListResponseDto> portfolioList = portfolioService.findMyAllPortfolios(authMember, memberId, pagecond);
        PageInfo pageInfo = PageInfo.builder()
                .pageNum(pagecond.getPageNum())
                .pageSize(pagecond.getPageSize())
                .totalElement(portfolioList.getTotalElements())
                .totalPage(portfolioList.getTotalPages())
                .build();

        return ResponseEntity.ok().body(ApiResponse.of(portfolioList.getContent(), pageInfo));
    }

    @Member
    @GetMapping("/members/{memberId}/{portfolioId}")
    public ResponseEntity<ApiResponse<PortfolioResponseDto>> findMyPortfolioDetail(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable("memberId") Long memberId,
            @PathVariable("portfolioId") Long portfolioId
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(portfolioService.findMyPortfolioDetail(authMember, memberId, portfolioId)));
    }

    @Member
    @PutMapping("/{portfolioId}")
    public ResponseEntity<ApiResponse<String>> updatePortfolio(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable("portfolioId") Long portfolioId,
            @Valid @RequestBody PortfolioUpdateRequestDto requestDto
    ) {
        portfolioService.updatePortfolio(authMember, portfolioId, requestDto);

        return ResponseEntity.ok(ApiResponse.of("포트폴리오 수정이 완료되었습니다."));
    }


    @Member
    @DeleteMapping("/{portfolioId}")
    public ResponseEntity<ApiResponse<Long>> deletePortfolio(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable("portfolioId") Long portfolioId
    ){
        return ResponseEntity.ok(ApiResponse.of(portfolioService.deletePortfolio(authMember, portfolioId)));
    }

}
