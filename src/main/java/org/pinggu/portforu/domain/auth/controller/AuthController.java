package org.pinggu.portforu.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.domain.auth.dto.response.SigninResponseDto;
import org.pinggu.portforu.domain.auth.dto.response.SignupResponseDto;
import org.pinggu.portforu.domain.auth.dto.request.SigninRequestDto;
import org.pinggu.portforu.domain.auth.dto.request.SignupRequestDto;
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
    public ResponseEntity<ApiResponse<SignupResponseDto>> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        return ResponseEntity.ok().body(ApiResponse.of(authService.signup(requestDto)));
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<SigninResponseDto>> signin(@Valid @RequestBody SigninRequestDto requestDto) {
        return ResponseEntity.ok().body(ApiResponse.of(authService.signin(requestDto)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<SigninResponseDto> refreshToken(
            @RequestHeader("Authorization") String bearerToken) {
        return ResponseEntity.ok(refreshTokenService.refreshToken(bearerToken));
    }

}
