package org.pinggu.portforu.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final long TOKEN_TIME = 60 * 60 * 1000L;    // 60분
    private static final long REFRESH_TOKEN_TIME = 7 * 24 * 60 * 60 * 1000L; // 7일

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(Long memberId, String email, String name, String phoneNumber, String address, UserRole userRole) {
        Date date = new Date();

        if (memberId == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST,"memberId는 null일 수 없습니다");
        }

        log.info("Creating access token for memberId: {}", memberId);

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(String.valueOf(memberId))
                        .claim("email", email)
                        .claim("name", name)
                        .claim("phoneNumber", phoneNumber)
                        .claim("address", address)
                        .claim("userRole", userRole.name())
                        .claim("tokenType", "access")
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String createRefreshToken(Long memberId, String email, String name, String phoneNumber, String address, UserRole userRole) {
        Date now = new Date();
        log.info("Creating refresh token for memberId: {}", memberId);

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(String.valueOf(memberId))
                        .claim("email", email)
                        .claim("name", name)
                        .claim("phoneNumber", phoneNumber)
                        .claim("address", address)
                        .claim("userRole", userRole.name())
                        .claim("tokenType", "refresh")
                        .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_TIME))
                        .setIssuedAt(now)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        throw new CustomException(HttpStatus.NOT_FOUND, "토큰을 찾을 수 없습니다.");
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}