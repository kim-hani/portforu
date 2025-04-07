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

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public PortfolioResponseDto savePortfolio(PortfolioRequestDto request, Long memberId){
        LocalDateTime now = LocalDateTime.now();
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
        return convertToDto(savedPortfolio);
    }


    @Transactional(readOnly = true)
    public Page<PortfolioResponseDto> findAllPortfolios(Pagecond pagecond){

        PageRequest pageRequest = PageRequest.of(pagecond.getPageNum()-1, pagecond.getPageSize());

        Page<Portfolio> portfolios = portfolioRepository.findAll(pageRequest);
        return portfolios.map(this::convertToDto);
    }


    @Transactional(readOnly = true)
    public PortfolioResponseDto findPortfolio(Long portfolioId){

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Portfolio not found"));

        portfolio.incrementViews();
        portfolioRepository.save(portfolio);
        return convertToDto(portfolio);
    }


    @Transactional
    public PortfolioResponseDto updatePortfolio(Long portfolioId, PortfolioUpdateRequestDto updateDto, Long memberId){

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,"게시물을 찾을 수 없습니다."));

        if(!portfolio.getMember().getId().equals(memberId)){
            throw new CustomException(HttpStatus.UNAUTHORIZED,"수정 권한이 없습니다.");
        }

        portfolio.update(updateDto.getTitle(), updateDto.getDescription(), updateDto.getFileUrl());

        Portfolio updatedPortfolio = portfolioRepository.save(portfolio);

        return convertToDto(updatedPortfolio);
    }


    @Transactional
    public void deletePortfolio(Long portfolioId, Long memberId){
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,"게시물을 찾을 수 없습니다."));

        if(!portfolio.getMember().getId().equals(memberId)){
            throw new CustomException(HttpStatus.UNAUTHORIZED,"수정 권한이 없습니다.");
        }

        // 소프트 삭제 방식 -> deletedAt으로 삭제를 관리함
        portfolio.delete();
        portfolioRepository.save(portfolio);
    }

    private PortfolioResponseDto convertToDto(Portfolio portfolio){
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
