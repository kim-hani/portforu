package org.pinggu.portforu.domain.portfolio.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.domain.Pagecond;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.member.repository.MemberRepository;
import org.pinggu.portforu.domain.portfolio.dto.request.PortfolioRequestDto;
import org.pinggu.portforu.domain.portfolio.dto.request.PortfolioUpdateRequestDto;
import org.pinggu.portforu.domain.portfolio.dto.response.PortfolioResponseDto;
import org.pinggu.portforu.domain.portfolio.entity.Portfolio;
import org.pinggu.portforu.domain.portfolio.repository.PortfolioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final MemberRepository memberRepository;

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

        Page<Portfolio> portfolioPage = portfolioRepository.findAllByDeletedAtIsNull(pageRequest);
        return portfolioPage.map(PortfolioResponseDto::from);
    }


    @Transactional
    public PortfolioResponseDto findPortfolio(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."));

        if (portfolio.isDeleted()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "삭제된 게시물입니다.");
        }
        portfolio.incrementViews();

        return PortfolioResponseDto.from(portfolio);
    }

    @Transactional
    public PortfolioResponseDto updatePortfolio(Long portfolioId, PortfolioUpdateRequestDto updateDto, Long memberId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."));

        if (portfolio.isDeleted()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "삭제된 게시물입니다.");
        }

        if (!portfolio.getMember().getId().equals(memberId)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "수정 권한이 없습니다.");
        }

        portfolio.update(updateDto.getTitle(), updateDto.getDescription(), updateDto.getFileUrl());
        return PortfolioResponseDto.from(portfolio);
    }

    @Transactional
    public Long deletePortfolio(Long portfolioId, Long memberId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."));

        if (portfolio.isDeleted()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "이미 삭제된 게시물입니다.");
        }

        if (!portfolio.getMember().getId().equals(memberId)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "수정 권한이 없습니다.");
        }

        return portfolio.delete();
    }

}