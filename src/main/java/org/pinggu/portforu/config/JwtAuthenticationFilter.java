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
    private static final List<String> EXCLUDE_URI_PREFIXES = List.of(
            "/v3/api-docs", "/swagger-ui", "/oauth2/", "/login/", "/error", "/favicon.ico"
    );

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            @NonNull HttpServletResponse res,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (EXCLUDE_URI_PREFIXES.stream().anyMatch(uri::startsWith)) {
            chain.doFilter(req, res);
            return;
        }

        String header = req.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            res.sendError(HttpStatus.UNAUTHORIZED.value(), "JWT 토큰이 필요합니다.");
            return;
        }

        String token = jwtUtil.substringToken(header);
        try {
            Claims claims = jwtUtil.extractClaims(token);
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                setAuthentication(claims);
            }
        } catch (MalformedJwtException | SecurityException e) {
            log.error("잘못된 JWT 서명", e);
            res.sendError(HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 JWT 서명입니다.");
            return;
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT", e);
            res.sendError(HttpStatus.UNAUTHORIZED.value(), "만료된 JWT 토큰입니다.");
            return;
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT", e);
            res.sendError(HttpStatus.BAD_REQUEST.value(), "지원되지 않는 JWT 토큰입니다.");
            return;
        } catch (Exception e) {
            log.error("JWT 검증 중 예외", e);
            res.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return;
        }

        chain.doFilter(req, res);
    }

    private void setAuthentication(Claims c) {
        Long id = Long.valueOf(c.getSubject());
        AuthMember member = new AuthMember(
                id,
                c.get("email", String.class),
                c.get("name", String.class),
                c.get("phoneNumber", String.class),
                c.get("address", String.class),
                UserRole.of(c.get("userRole", String.class)),
                c.get("provider", String.class)
        );
        var authToken = new JwtAuthenticationToken(member);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
