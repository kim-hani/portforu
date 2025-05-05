package org.pinggu.portforu.domain.oauth.handler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.domain.RefreshToken;
import org.pinggu.portforu.config.JwtUtil;
import org.pinggu.portforu.domain.auth.repository.RefreshTokenRepository;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.oauth.user.CustomOAuth2User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        Member member = oAuth2User.getMember();

        //  토큰 생성
        String accessToken = jwtUtil.createToken(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getPhoneNumber(),
                member.getAddress(),
                member.getUserRole(),
                member.getProvider()
        );

        String refreshToken = jwtUtil.createRefreshToken(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getPhoneNumber(),
                member.getAddress(),
                member.getUserRole(),
                member.getProvider()
        );

        //  RefreshToken 저장
        refreshTokenRepository.findById(member.getId()).ifPresentOrElse(
                existing -> existing.updateToken(refreshToken),
                () -> refreshTokenRepository.save(
                        RefreshToken.builder()
                                .memberId(member.getId())
                                .token(refreshToken)
                                .build()
                )
        );

        //  쿠키 설정
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(60 * 60); // 1시간

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7일

        //  응답에 쿠키 추가
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        //  리다이렉트
        response.sendRedirect("https://pinggu.vercel.app/mainpage");
    }
}

