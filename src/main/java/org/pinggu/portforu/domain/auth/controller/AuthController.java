package org.pinggu.portforu.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.domain.Response;
import org.pinggu.portforu.domain.auth.dto.reponse.SignupResponseDto;
import org.pinggu.portforu.domain.auth.dto.request.SigninRequestDto;
import org.pinggu.portforu.domain.auth.dto.request.SignupRequestDto;
import org.pinggu.portforu.domain.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Response<SignupResponseDto>> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        return ResponseEntity.ok().body(Response.of(authService.signup(requestDto)));
    }

    @PostMapping("/signin")
    public ResponseEntity<Response<String>> signin(@Valid @RequestBody SigninRequestDto requestDto) {
        return ResponseEntity.ok().body(Response.of(authService.signin(requestDto)));
    }


}
