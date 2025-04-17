package org.pinggu.portforu.domain.auth.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pinggu.portforu.common.domain.RefreshToken;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.config.JwtUtil;
import org.pinggu.portforu.domain.auth.dto.request.SignInRequestDto;
import org.pinggu.portforu.domain.auth.dto.request.SignUpRequestDto;
import org.pinggu.portforu.domain.auth.dto.response.SignInResponseDto;
import org.pinggu.portforu.domain.auth.dto.response.SignUpResponseDto;
import org.pinggu.portforu.domain.auth.repository.RefreshTokenRepository;
import org.pinggu.portforu.domain.auth.service.AuthService;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.pinggu.portforu.domain.member.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock private MemberRepository memberRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtUtil jwtUtil;
    @Mock private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks private AuthService authService;

    private static final String EMAIL = "yumi@example.com";
    private static final String PASSWORD = "password!";
    private static final String ENCODED_PASSWORD = "encodedPassword123!";
    private static final String NAME = "yumi";
    private static final String PHONE = "010-1234-0410";
    private static final String ADDRESS = "고양시 야옹동";
    private static final Long MEMBER_ID = 1L;
    private static final String ACCESS_TOKEN = "Bearer accessToken";
    private static final String REFRESH_TOKEN = "Bearer refreshToken";

    private Member member;

    private SignUpRequestDto signUpRequest() {
        return new SignUpRequestDto(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
    }

    private SignInRequestDto signInRequest() {
        return new SignInRequestDto(EMAIL, PASSWORD);
    }

    private Member saveMember() {
        Member savedMember = Member.builder()
                .email(EMAIL)
                .password(ENCODED_PASSWORD)
                .name(NAME)
                .phoneNumber(PHONE)
                .address(ADDRESS)
                .userRole(UserRole.ROLE_USER)
                .build();
        ReflectionTestUtils.setField(savedMember, "id", MEMBER_ID);
        return savedMember;
    }

    @BeforeEach
    void setUp() {
        member = saveMember();
    }

    @Nested
    class 회원_가입 {
        @Test
        void 회원_가입_성공() {
            // given
            SignUpRequestDto request = signUpRequest();

            given(memberRepository.existsByEmail(EMAIL)).willReturn(false);
            given(passwordEncoder.encode(PASSWORD)).willReturn(ENCODED_PASSWORD);
            given(memberRepository.save(any(Member.class))).willAnswer(inv -> {
                Member saved = inv.getArgument(0);
                ReflectionTestUtils.setField(saved, "id", MEMBER_ID);
                return saved;
            });
            given(jwtUtil.createToken(anyLong(), anyString(), anyString(), anyString(), anyString(), any(UserRole.class)))
                    .willReturn(ACCESS_TOKEN);
            given(jwtUtil.createRefreshToken(anyLong(), anyString(), anyString(), anyString(), anyString(), any(UserRole.class)))
                    .willReturn(REFRESH_TOKEN);

            // when
            SignUpResponseDto response = authService.signUp(request);

            // then
            assertThat(response)
                    .isNotNull()
                    .extracting(
                            SignUpResponseDto::getBearerToken,
                            SignUpResponseDto::getId,
                            SignUpResponseDto::getEmail,
                            SignUpResponseDto::getName,
                            SignUpResponseDto::getAddress,
                            SignUpResponseDto::getUserRole
                    )
                    .containsExactly(ACCESS_TOKEN, MEMBER_ID, EMAIL, NAME, PHONE, ADDRESS, UserRole.ROLE_USER.name());

            then(memberRepository).should().save(any(Member.class));
        }

        @Test
        void 중복된_이메일_입력시_에러_발생() {
            // given
            SignUpRequestDto request = signUpRequest();

            given(memberRepository.existsByEmail(EMAIL)).willReturn(true);

            // when & then
            assertThatThrownBy(() -> authService.signUp(request))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.BAD_REQUEST)
                    .hasMessage("이미 존재하는 이메일입니다.");

            then(memberRepository).should(never()).save(any(Member.class));
        }
    }

    @Nested
    class 로그인 {
        @Test
        void 로그인_성공() {
            // given
            SignInRequestDto request = signInRequest();

            given(memberRepository.findByEmail(EMAIL)).willReturn(Optional.of(member));
            given(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).willReturn(true);
            given(jwtUtil.createToken(anyLong(), anyString(), anyString(), anyString(), anyString(), any(UserRole.class))).willReturn(ACCESS_TOKEN);
            given(jwtUtil.createRefreshToken(anyLong(), anyString(), anyString(), anyString(), anyString(), any(UserRole.class))).willReturn(REFRESH_TOKEN);
            given(refreshTokenRepository.findById(anyLong())).willReturn(Optional.empty());

            // when
            SignInResponseDto response = authService.signIn(request);

            // then
            assertThat(response)
                    .isNotNull()
                    .extracting(
                            SignInResponseDto::getAccessToken,
                            SignInResponseDto::getRefreshToken
                    )
                    .containsExactly(ACCESS_TOKEN, REFRESH_TOKEN);

            then(refreshTokenRepository).should().save(any(RefreshToken.class));

        }

        @Test
        void 존재하지_않는_이메일로_로그인_시도하면_에러_발생() {
            // given
            SignInRequestDto request = new SignInRequestDto("email", PASSWORD);

            given(memberRepository.findByEmail("email")).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> authService.signIn(request))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.BAD_REQUEST)
                    .hasMessage("가입되지 않은 유저입니다.");
        }

        @Test
        void 잘못된_비밀번호로_로그인_시도하면_에러_발생() {
            // given
            SignInRequestDto request = new SignInRequestDto(EMAIL, "wrongPassword");

            given(memberRepository.findByEmail(EMAIL)).willReturn(Optional.of(member));
            given(passwordEncoder.matches("wrongPassword", ENCODED_PASSWORD)).willReturn(false);

            // when & then
            assertThatThrownBy(() -> authService.signIn(request))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.UNAUTHORIZED)
                    .hasMessage("잘못된 비밀번호입니다.");
        }
    }

    // (1) BDDMockito 적용(given, when, then 구조)
    // given().willReturn
    // then().should().method();

    // (2) AssertJ 도입(풍부한 검증 메소드)
    // assertThat(actual).isEqualTo(expected)
    // .hasFieldOrProperty()
    // .containsExactly();

    // (3) 팩토리 메소드 사용 -> 중복 사용되는 메소드 선언

    // (4) 테스트 데이터 상수로 선언


}
