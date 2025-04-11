package org.pinggu.portforu.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private static final List<String> EXCLUDE_URI_PREFIXES = List.of("/oauth2/", "/login/", "/error", "/favicon.ico");

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpRequest,
            @NonNull HttpServletResponse httpResponse,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {

        String uri = httpRequest.getRequestURI();

        if (isExcludedUri(uri)) {
            chain.doFilter(httpRequest, httpResponse);
            return;
        }

        String authorizationHeader = httpRequest.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = jwtUtil.substringToken(authorizationHeader);
            try {
                Claims claims = jwtUtil.extractClaims(jwt);

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    setAuthentication(claims);
                }
            } catch (SecurityException | MalformedJwtException e) {
                log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.", e);
                httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "유효하지 않는 JWT 서명입니다.");
                return;
            } catch (ExpiredJwtException e) {
                log.error("Expired JWT token, 만료된 JWT token 입니다.", e);
                httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "만료된 JWT 토큰입니다.");
                return;
            } catch (UnsupportedJwtException e) {
                log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.", e);
                httpResponse.sendError(HttpStatus.BAD_REQUEST.value(), "지원되지 않는 JWT 토큰입니다.");
                return;
            } catch (Exception e) {
                log.error("Internal server error", e);
                httpResponse.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return;
            }
        }
        chain.doFilter(httpRequest, httpResponse);
    }

    private boolean isExcludedUri(String uri) {
        return EXCLUDE_URI_PREFIXES.stream().anyMatch(uri::startsWith);
    }

    private void setAuthentication(Claims claims) {
        Long memberId = Long.valueOf(claims.getSubject());
        String email = claims.get("email", String.class);
        String name = claims.get("name", String.class);
        String phoneNumber = claims.get("phoneNumber", String.class);
        String address = claims.get("address", String.class);
        UserRole userRole = UserRole.of(claims.get("userRole", String.class));

        AuthMember authMember = new AuthMember(memberId, email, name, phoneNumber, address, userRole);
        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(authMember);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

}