package org.pinggu.portforu.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.domain.RefreshToken;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.config.JwtUtil;
import org.pinggu.portforu.domain.auth.dto.response.SignInResponseDto;
import org.pinggu.portforu.domain.auth.dto.response.SignUpResponseDto;
import org.pinggu.portforu.domain.auth.dto.request.SignInRequestDto;
import org.pinggu.portforu.domain.auth.dto.request.SignUpRequestDto;
import org.pinggu.portforu.domain.auth.repository.RefreshTokenRepository;
import org.pinggu.portforu.domain.auth.verify.PhoneVerification;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.pinggu.portforu.domain.member.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PhoneVerification phoneVerification;

    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto requestDto) {
        String normalizedEmail = requestDto.getEmail().toLowerCase();
        String phoneNumber = requestDto.getPhoneNumber();

        if (memberRepository.existsByEmail(normalizedEmail)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.");
        }

        if(!phoneVerification.isVerified(phoneNumber)){
            throw new CustomException(HttpStatus.BAD_REQUEST,"전화번호 인증이 완료되지 않았습니다");
        }

        Member newMember = Member.builder()
                .email(normalizedEmail)
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .name(requestDto.getName())
                .phoneNumber(requestDto.getPhoneNumber())
                .address(requestDto.getAddress())
                .userRole(UserRole.ROLE_USER)
                .build();

        Member savedMember = memberRepository.save(newMember);

        String accessToken = jwtUtil.createToken(
                savedMember.getId(),
                savedMember.getEmail(),
                savedMember.getName(),
                savedMember.getPhoneNumber(),
                savedMember.getAddress(),
                savedMember.getUserRole(),
                savedMember.getProvider()
        );
        String refreshToken = jwtUtil.createRefreshToken(
                savedMember.getId(),
                savedMember.getEmail(),
                savedMember.getName(),
                savedMember.getPhoneNumber(),
                savedMember.getAddress(),
                savedMember.getUserRole(),
                savedMember.getProvider()
        );

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .memberId(savedMember.getId())
                        .token(refreshToken)
                        .build()
        );

        return SignUpResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .id(savedMember.getId())
                .email(savedMember.getEmail())
                .name(savedMember.getName())
                .phoneNumber(savedMember.getPhoneNumber())
                .address(savedMember.getAddress())
                .userRole(savedMember.getUserRole().name())
                .build();
    }

    @Transactional
    public SignInResponseDto signIn(SignInRequestDto requestDto) {
        String normalizedEmail = requestDto.getEmail().toLowerCase();

        Member member = memberRepository.findByEmail(normalizedEmail).orElseThrow(
                () -> new CustomException(HttpStatus.BAD_REQUEST, "가입되지 않은 유저입니다.")
        );

        if (member.getIsDeleted()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "탈퇴한 사용자입니다.");
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호입니다.");
        }

        String accessToken = jwtUtil.createToken(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getPhoneNumber(),
                member.getAddress(),
                member.getUserRole(),
                member.getProvider()
        );

        String refreshToken = jwtUtil.createRefreshToken(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getPhoneNumber(),
                member.getAddress(),
                member.getUserRole(),
                member.getProvider()
        );

        refreshTokenRepository.findById(member.getId()).ifPresentOrElse(existing -> existing.updateToken(refreshToken),
                () -> refreshTokenRepository.save(
                        RefreshToken.builder()
                                .memberId(member.getId())
                                .token(refreshToken)
                                .build()
                )
        );

        return new SignInResponseDto(accessToken, refreshToken);
    }

}
