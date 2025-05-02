package org.pinggu.portforu.domain.portfolio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "포트폴리오 관리 API", description = "회원별 포트폴리오 생성·수정·삭제 및 조회")
@RestController
@RequestMapping("/api/v1/portfolios")
@RequiredArgsConstructor
public class PortfolioMemberController {

    private final PortfolioService portfolioService;

    @Operation(summary = "포트폴리오 생성", description = "로그인 회원이 새 포트폴리오를 생성합니다.")
    @Member
    @PostMapping
    public ResponseEntity<ApiResponse<PortfolioResponseDto>> savePortfolio(
            @AuthenticationPrincipal AuthMember authMember,
            @Valid @RequestBody PortfolioRequestDto requestDto
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                portfolioService.savePortfolio(authMember, requestDto)
        ));
    }

    @Operation(summary = "내 포트폴리오 목록 조회", description = "로그인 회원의 모든 포트폴리오를 조회합니다.")
    @Member
    @GetMapping("/members/{memberId}")
    public ResponseEntity<ApiResponse<List<PortfolioListResponseDto>>> findMyAllPortfolios(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable Long memberId,
            @ModelAttribute Pagecond pagecond
    ) {
        Page<PortfolioListResponseDto> page = portfolioService.findMyAllPortfolios(authMember, memberId, pagecond);
        PageInfo info = PageInfo.builder()
                .pageNum(pagecond.getPageNum())
                .pageSize(pagecond.getPageSize())
                .totalElement(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .build();
        return ResponseEntity.ok(ApiResponse.of(page.getContent(), info));
    }

    @Operation(summary = "내 포트폴리오 상세 조회", description = "로그인 회원의 특정 포트폴리오 상세를 조회합니다.")
    @Member
    @GetMapping("/members/{memberId}/{portfolioId}")
    public ResponseEntity<ApiResponse<PortfolioResponseDto>> findMyPortfolioDetail(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable Long memberId,
            @PathVariable Long portfolioId
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                portfolioService.findMyPortfolioDetail(authMember, memberId, portfolioId)
        ));
    }

    @Operation(summary = "포트폴리오 수정", description = "로그인 회원의 포트폴리오를 수정합니다.")
    @Member
    @PutMapping("/{portfolioId}")
    public ResponseEntity<ApiResponse<String>> updatePortfolio(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable Long portfolioId,
            @Valid @RequestBody PortfolioUpdateRequestDto requestDto
    ) {
        portfolioService.updatePortfolio(authMember, portfolioId, requestDto);
        return ResponseEntity.ok(ApiResponse.of("포트폴리오 수정이 완료되었습니다."));
    }

    @Operation(summary = "포트폴리오 삭제", description = "로그인 회원의 포트폴리오를 삭제합니다.")
    @Member
    @DeleteMapping("/{portfolioId}")
    public ResponseEntity<ApiResponse<Long>> deletePortfolio(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable Long portfolioId
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                portfolioService.deletePortfolio(authMember, portfolioId)
        ));
    }
}
