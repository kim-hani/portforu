package org.pinggu.portforu.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.auth.dto.response.SignInResponseDto;
import org.pinggu.portforu.domain.auth.dto.response.SignUpResponseDto;
import org.pinggu.portforu.domain.auth.dto.request.SignInRequestDto;
import org.pinggu.portforu.domain.auth.dto.request.SignUpRequestDto;
import org.pinggu.portforu.domain.auth.service.AuthService;
import org.pinggu.portforu.domain.auth.service.RefreshTokenService;
import org.pinggu.portforu.domain.auth.service.SmsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "인증 API", description = "회원가입·로그인·토큰 갱신")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final SmsService smsService;

    @Operation(summary = "회원 가입", description = "이메일·비밀번호로 신규 회원을 등록합니다.")
    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<SignUpResponseDto>> signUp(
            @Valid @RequestBody SignUpRequestDto requestDto
    ) {
        return ResponseEntity.ok(ApiResponse.of(authService.signUp(requestDto)));
    }

    @Operation(summary = "로그인", description = "이메일·비밀번호로 로그인하고 JWT를 발급합니다.")
    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<SignInResponseDto>> signIn(
            @Valid @RequestBody SignInRequestDto requestDto
    ) {
        return ResponseEntity.ok(ApiResponse.of(authService.signIn(requestDto)));
    }

    @Operation(summary = "토큰 갱신", description = "리프레시 토큰으로 JWT를 갱신합니다.")
    @PostMapping("/refresh")
    public ResponseEntity<SignInResponseDto> refreshToken(
            @RequestHeader("Authorization") String bearerToken
    ) {
        return ResponseEntity.ok(refreshTokenService.refreshToken(bearerToken));
    }

    @Operation(summary = "인증번호 전송", description = "휴대폰 번호로 인증번호를 전송합니다.")
    @PostMapping("/phone/send")
    public ResponseEntity<ApiResponse<String>> sendVerificationCode(@RequestParam String phoneNumber) {
        smsService.sendVerificationCode(phoneNumber);
        return ResponseEntity.ok(ApiResponse.of("인증번호가 전송되었습니다."));
    }

    @Operation(summary = "인증번호 검증", description = "사용자가 입력한 인증번호를 검증합니다.")
    @PostMapping("/phone/verify")
    public ResponseEntity<ApiResponse<String>> verifyCode(
            @RequestParam String phoneNumber,
            @RequestParam String code
    ) {
        if (smsService.verifyCode(phoneNumber, code)) {
            return ResponseEntity.ok(ApiResponse.of("인증이 완료되었습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.of("인증번호가 올바르지 않습니다."));
        }
    }

}
