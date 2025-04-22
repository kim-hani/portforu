package org.pinggu.portforu.domain.portfolio.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.portfolio.entity.Portfolio;
import org.pinggu.portforu.domain.portfolio.repository.PortfolioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioFinder {

    private final PortfolioRepository portfolioRepository;

    public Portfolio findPortfolioById(Long id) {
        return portfolioRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."));
    }

}