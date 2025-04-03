package org.pinggu.portforu.domain.auth.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.domain.RefreshToken;
import org.pinggu.portforu.common.domain.Response;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.config.JwtUtil;
import org.pinggu.portforu.domain.auth.dto.response.SigninResponseDto;
import org.pinggu.portforu.domain.auth.dto.response.SignupResponseDto;
import org.pinggu.portforu.domain.auth.dto.request.SigninRequestDto;
import org.pinggu.portforu.domain.auth.dto.request.SignupRequestDto;
import org.pinggu.portforu.domain.auth.repository.RefreshTokenRepository;
import org.pinggu.portforu.domain.auth.service.AuthService;
import org.pinggu.portforu.domain.user.entity.User;
import org.pinggu.portforu.domain.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;


    @PostMapping("/signup")
    public ResponseEntity<Response<SignupResponseDto>> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        return ResponseEntity.ok().body(Response.of(authService.signup(requestDto)));
    }

    @PostMapping("/signin")
    public ResponseEntity<Response<SigninResponseDto>> signin(@Valid @RequestBody SigninRequestDto requestDto) {
        return ResponseEntity.ok().body(Response.of(authService.signin(requestDto)));
    }


    @PostMapping("/refresh")
    public ResponseEntity<SigninResponseDto> refreshToken(
            @RequestHeader("Authorization") String bearerToken) {

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

        Long userId = Long.parseLong(claims.getSubject());

        RefreshToken saved = refreshTokenRepository.findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.UNAUTHORIZED, "Refresh Token 없음"));

        if (!saved.getToken().equals(bearerToken)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Refresh Token 불일치");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "유저 없음"));

        String newAccessToken = jwtUtil.createToken(user.getId(),
                user.getEmail(),
                user.getUserRole()
        );
        String newRefreshToken = jwtUtil.createRefreshToken(user.getId(),
                user.getEmail(),
                user.getUserRole()
        );
        saved.updateToken(newRefreshToken);

        return ResponseEntity.ok(new SigninResponseDto(newAccessToken, newRefreshToken));
    }
}
