package org.pinggu.portforu.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.config.JwtUtil;
import org.pinggu.portforu.domain.auth.dto.reponse.SignupResponseDto;
import org.pinggu.portforu.domain.auth.dto.request.SigninRequestDto;
import org.pinggu.portforu.domain.auth.dto.request.SignupRequestDto;
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

    @Transactional
    public SignupResponseDto signup(SignupRequestDto requestDto) {

        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User newUser = new User(
                requestDto.getEmail(),
                encodedPassword,
                UserRole.of(requestDto.getUserRole())
        );
        userRepository.save(newUser);

        return SignupResponseDto.builder()
                .id(newUser.getId())
                .email(newUser.getEmail())
                .userRole(String.valueOf(newUser.getUserRole()))
                .build();
    }

    @Transactional
    public String signin(SigninRequestDto requestDto) {
        User findUser = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(() ->
                new CustomException(HttpStatus.BAD_REQUEST, "존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.createToken(findUser.getId(), findUser.getEmail(), findUser.getUserRole());
    }

}
