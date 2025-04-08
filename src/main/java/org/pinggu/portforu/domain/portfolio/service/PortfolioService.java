package org.pinggu.portforu.domain.portfolio.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.domain.Pagecond;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.comment.dto.response.CommentResponseDto;
import org.pinggu.portforu.domain.comment.service.CommentService;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.member.repository.MemberRepository;
import org.pinggu.portforu.domain.portfolio.dto.request.PortfolioRequestDto;
import org.pinggu.portforu.domain.portfolio.dto.request.PortfolioUpdateRequestDto;
import org.pinggu.portforu.domain.portfolio.dto.response.PortfolioDetailResponseDto;
import org.pinggu.portforu.domain.portfolio.dto.response.PortfolioResponseDto;
import org.pinggu.portforu.domain.portfolio.entity.Portfolio;
import org.pinggu.portforu.domain.portfolio.repository.PortfolioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final MemberRepository memberRepository;
    private final CommentService commentService;

    @Transactional
    public PortfolioResponseDto savePortfolio(PortfolioRequestDto request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."));

        Portfolio portfolio = Portfolio.builder()
                .member(member)
                .title(request.getTitle())
                .description(request.getDescription())
                .fileUrl(request.getFileUrl())
                .views(0)
                .build();

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        return PortfolioResponseDto.from(savedPortfolio);
    }

    @Transactional(readOnly = true)
    public Page<PortfolioResponseDto> findAllPortfolios(Pagecond pagecond) {
        PageRequest pageRequest = PageRequest.of(pagecond.getPageNum() - 1, pagecond.getPageSize());
        Page<Portfolio> portfolios = portfolioRepository.findAllByDeletedAtIsNull(pageRequest);
        
      return portfolios.map(PortfolioResponseDto::from);
    }


    @Transactional
    public PortfolioDetailResponseDto findPortfolio(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findByIdAndDeletedAtIsNull(portfolioId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."));

        portfolio.incrementViews();
        portfolioRepository.save(portfolio);

        List<CommentResponseDto> comments = commentService.findAllComments(portfolio.getId());
        return PortfolioDetailResponseDto.from(portfolio, comments);
    }

    @Transactional
    public PortfolioResponseDto updatePortfolio(Long portfolioId, PortfolioUpdateRequestDto updateDto, Long memberId) {
        Portfolio portfolio = portfolioRepository.findByIdAndDeletedAtIsNull(portfolioId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."));

        if (!portfolio.getMember().getId().equals(memberId)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "수정 권한이 없습니다.");
        }

        portfolio.update(updateDto.getTitle(), updateDto.getDescription(), updateDto.getFileUrl());
        Portfolio updatedPortfolio = portfolioRepository.save(portfolio);

        return PortfolioResponseDto.from(updatedPortfolio);
    }

    @Transactional
    public void deletePortfolio(Long portfolioId, Long memberId) {
        Portfolio portfolio = portfolioRepository.findByIdAndDeletedAtIsNull(portfolioId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."));

        if (!portfolio.getMember().getId().equals(memberId)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "수정 권한이 없습니다.");
        }
      
        return portfolio.delete();
    }

}