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
import org.pinggu.portforu.domain.subscribe.validator.SubscribeValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    private final SubscribeValidator subscribeValidator;

    @Transactional
    public PortfolioResponseDto savePortfolio(PortfolioRequestDto request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."));

        String fileUrl = null;
        try {
            if (request.getImageFile() != null && !request.getImageFile().isEmpty()) {
                fileUrl = s3Service.uploadImage(request.getImageFile());
            }
        } catch (IOException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "파일업로드에 실패하였습니다.");
        }

        Portfolio portfolio = Portfolio.builder()
                .member(member)
                .title(request.getTitle())
                .description(request.getDescription())
                .fileUrl(fileUrl)
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
    public PortfolioResponseDto findPortfolio(Long portfolioId,Long viewerId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."));

        if (portfolio.isDeleted()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "삭제된 게시물입니다.");
        }

        if (!portfolio.getMember().getId().equals(viewerId)) {

            if (!subscribeValidator.isSubscribed(viewerId)) {
                Member viewer = memberRepository.findById(viewerId)
                        .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."));
                if (viewer.getViewCount() <= 0) {
                    throw new CustomException(HttpStatus.FORBIDDEN, "포트폴리오 조회 가능 횟수를 모두 사용했습니다.");
                }
                viewer.decrementRemainingViewCount();
            }
        }

        portfolio.incrementViews();

        return PortfolioResponseDto.from(portfolio);
    }

    // 마이페이지에서 내가 올린 포트폴리오만 조회하기
    @Transactional(readOnly = true)
    public Page<PortfolioResponseDto> findMyAllPortfolios(Long memberId,Pagecond pagecond) {

        PageRequest pageRequest = PageRequest.of(pagecond.getPageNum() - 1, pagecond.getPageSize());
        Page<Portfolio> portfolios = portfolioRepository.findAllByMemberIdAndDeletedAtIsNull(memberId, pageRequest);
        if(portfolios.isEmpty()){
            throw new CustomException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다.");
        }
        return portfolios.map(PortfolioResponseDto::from);
    }

    @Transactional
    public PortfolioResponseDto findMyPortfolioDetail(Long portfolioId, Long memberId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."));

        if (portfolio.isDeleted()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "삭제된 게시물입니다.");
        }

        if (!portfolio.getMember().getId().equals(memberId)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "게시물에 대한 접근 권한이 없습니다.");
        }

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

        String newFileUrl = portfolio.getFileUrl();
        try {
            if (updateDto.getImageFile() != null && !updateDto.getImageFile().isEmpty()) {
                if (portfolio.getFileUrl() != null && !portfolio.getFileUrl().isBlank()) {
                    s3Service.markFileAsInactive(portfolio.getFileUrl());
                }

                newFileUrl = s3Service.uploadImage(updateDto.getImageFile());
            }
        } catch (IOException e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다.");
        }

        Integer updatedRows = portfolioRepository.updatePortfolio(
                portfolioId,
                updateDto.getTitle(),
                updateDto.getDescription(),
                newFileUrl
        );

        if (updatedRows <= 0) {
            throw new CustomException(HttpStatus.NOT_MODIFIED, "수정 사항이 없습니다.");
        }

        Portfolio updatedPortfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."));

        return PortfolioResponseDto.from(updatedPortfolio);
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