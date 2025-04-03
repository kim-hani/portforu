package org.pinggu.portforu.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.domain.RefreshToken;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.config.JwtUtil;
import org.pinggu.portforu.domain.auth.dto.response.SigninResponseDto;
import org.pinggu.portforu.domain.auth.dto.response.SignupResponseDto;
import org.pinggu.portforu.domain.auth.dto.request.SigninRequestDto;
import org.pinggu.portforu.domain.auth.dto.request.SignupRequestDto;
import org.pinggu.portforu.domain.auth.repository.RefreshTokenRepository;
import org.pinggu.portforu.domain.user.entity.User;
import org.pinggu.portforu.domain.user.entity.UserRole;
import org.pinggu.portforu.domain.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto requestDto) {

        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        UserRole userRole = UserRole.ROLE_USER;

        User newUser = new User(
                requestDto.getEmail(),
                encodedPassword,
                userRole
        );

        User savedUser = userRepository.save(newUser);

        String accessToken = jwtUtil.createToken(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getUserRole()
        );


        String refreshToken = jwtUtil.createRefreshToken(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getUserRole()
        );

        return new SignupResponseDto(
                accessToken,
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getUserRole().name()
        );
    }

    @Transactional(readOnly = true)
    public SigninResponseDto signin(SigninRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new CustomException(HttpStatus.BAD_REQUEST, "가입되지 않은 유저입니다.")
        );

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호입니다.");
        }

        String accessToken = jwtUtil.createToken(
                user.getId(),
                user.getEmail(),
                user.getUserRole()
        );
        String refreshToken = jwtUtil.createRefreshToken(
                user.getId(),
                user.getEmail(),
                user.getUserRole()
        );


        refreshTokenRepository.findById(user.getId())
                .ifPresentOrElse(
                        existing -> existing.updateToken(refreshToken),
                        () -> refreshTokenRepository.save(new RefreshToken(user.getId(), refreshToken))
                );

        return new SigninResponseDto(accessToken, refreshToken);
    }

}
