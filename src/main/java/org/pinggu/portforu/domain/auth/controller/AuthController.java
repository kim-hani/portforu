package org.pinggu.portforu.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.domain.auth.dto.response.SignInResponseDto;
import org.pinggu.portforu.domain.auth.dto.response.SignUpResponseDto;
import org.pinggu.portforu.domain.auth.dto.request.SignInRequestDto;
import org.pinggu.portforu.domain.auth.dto.request.SignUpRequestDto;
import org.pinggu.portforu.domain.auth.service.AuthService;
import org.pinggu.portforu.domain.auth.service.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignUpResponseDto>> signUp(
            @Valid @RequestBody SignUpRequestDto requestDto
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(authService.signUp(requestDto)));
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<SignInResponseDto>> signIn(
            @Valid @RequestBody SignInRequestDto requestDto
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(authService.signIn(requestDto)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<SignInResponseDto> refreshToken(
            @RequestHeader("Authorization") String bearerToken
    ) {
        return ResponseEntity.ok(refreshTokenService.refreshToken(bearerToken));
    }

}
