package org.pinggu.portforu.domain.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.domain.RefreshToken;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.config.JwtUtil;
import org.pinggu.portforu.domain.auth.dto.response.SignInResponseDto;
import org.pinggu.portforu.domain.auth.repository.RefreshTokenRepository;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.member.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    public SignInResponseDto refreshToken(String bearerToken) {
        String token = jwtUtil.substringToken(bearerToken);
        Claims claims;

        try {
            claims = jwtUtil.extractClaims(token);
        } catch (ExpiredJwtException e) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Refresh Token 만료. 다시 로그인 필요");
        }

        String tokenType = claims.get("tokenType", String.class);
        if (!"refresh".equalsIgnoreCase(tokenType)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Access Token이 제공되었습니다. Refresh Token 필요");
        }

        Long memberId = Long.parseLong(claims.getSubject());

        RefreshToken saved = refreshTokenRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(HttpStatus.UNAUTHORIZED, "Refresh Token 없음"));

        if (!saved.getToken().equals(bearerToken)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Refresh Token 불일치");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "유저 없음"));

        String newAccessToken = jwtUtil.createToken(member.getId(),
                member.getEmail(),
                member.getName(),
                member.getPhoneNumber(),
                member.getAddress(),
                member.getUserRole()
        );
        String newRefreshToken = jwtUtil.createRefreshToken(member.getId(),
                member.getEmail(),
                member.getName(),
                member.getPhoneNumber(),
                member.getAddress(),
                member.getUserRole()
        );
        saved.updateToken(newRefreshToken);

        return new SignInResponseDto(newAccessToken, newRefreshToken);
    }

}
