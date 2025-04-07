package org.pinggu.portforu.member.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.member.dto.request.MemberDeleteRequestDto;
import org.pinggu.portforu.domain.member.dto.request.MemberUpdateRequestDto;
import org.pinggu.portforu.domain.member.dto.response.MemberResponseDto;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.pinggu.portforu.domain.member.repository.MemberRepository;
import org.pinggu.portforu.domain.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.pinggu.portforu.domain.member.entity.Member;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock private MemberRepository memberRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks private MemberService memberService;

    private static final String EMAIL = "yumi@example.com";
    private static final String PASSWORD = "password!";
    private static final String ENCODED_PASSWORD = "encodedPassword123!";
    private static final String NAME = "yumi";
    private static final String PHONE = "010-1234-0410";
    private static final String ADDRESS = "고양시 야옹동";
    private static final Long MEMBER_ID = 1L;

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member(EMAIL, ENCODED_PASSWORD, NAME, PHONE, ADDRESS, UserRole.ROLE_USER);
        ReflectionTestUtils.setField(member, "id", MEMBER_ID);
    }

    private AuthMember authMember() {
        return new AuthMember(MEMBER_ID, EMAIL, NAME, PHONE, ADDRESS, UserRole.ROLE_USER);
    }

    @Nested
    class 회원_조회 {
        @Test
        void 회원_조회_성공() {
            // given
            AuthMember authMember = authMember();

            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(member));

            // when
            MemberResponseDto response = memberService.findMember(authMember);

            // then
            assertThat(response)
                    .isNotNull()
                    .extracting(
                            MemberResponseDto::getEmail,
                            MemberResponseDto::getName,
                            MemberResponseDto::getPhoneNumber,
                            MemberResponseDto::getAddress
                    )
                    .containsExactly(EMAIL, NAME, PHONE, ADDRESS);

            then(memberRepository).should().findById(MEMBER_ID);
        }

        @Test
        void 존재하지_않는_회원_조화_시_에러_발생() {
            // given
            AuthMember authMember = authMember();
            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> memberService.findMember(authMember))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND)
                    .hasMessage("존재하지 않는 회원정보입니다.");
        }
    }

    @Nested
    class 회원_정보_수정 {
        @Test
        void 회원_정보_수정_성공() {
            // given
            AuthMember authMember = authMember();
            MemberUpdateRequestDto request = new MemberUpdateRequestDto(
                    PASSWORD,
                    "passwordUpdate!",
                    "nameUpdate",
                    "010-1111-2222",
                    "유미시 유미구"
            );

            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(member));
            given(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).willReturn(true);
            given(passwordEncoder.encode(request.getNewPassword())).willReturn("newEncodedPassword");

            // when
            MemberResponseDto response = memberService.updateMember(authMember, request);

            // then
            assertThat(response)
                    .isNotNull()
                    .extracting(
                            MemberResponseDto::getName,
                            MemberResponseDto::getPhoneNumber,
                            MemberResponseDto::getAddress
                    )
                    .containsExactly("nameUpdate", "010-1111-2222", "유미시 유미구");
        }

        @Test
        void 기존_비밀번호_불일치_시_에러_발생() {
            // given
            AuthMember authMember = authMember();
            MemberUpdateRequestDto request = new MemberUpdateRequestDto(
                    "wrongPassword",
                    "newPassword!",
                    NAME,
                    PHONE,
                    ADDRESS
            );

            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(member));
            given(passwordEncoder.matches("wrongPassword", ENCODED_PASSWORD)).willReturn(false);

            // when & then
            assertThatThrownBy(() -> memberService.updateMember(authMember, request))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.BAD_REQUEST)
                    .hasMessage("기존 비밀번호가 일치하지 않습니다.");
        }
    }

    @Nested
    class 회원_삭제 {
        @Test
        void 회원_삭제_성공() {
            // given
            AuthMember authMember = authMember();
            MemberDeleteRequestDto request = new MemberDeleteRequestDto(PASSWORD, PASSWORD);

            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(member));
            given(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).willReturn(true);

            // When
            memberService.deleteMember(authMember, request);

            // Then
            then(memberRepository).should().findById(MEMBER_ID);
        }

        @Test
        void 회원_삭제_중_비밀번호_불일치_시_에러_발생() {
            // given
            AuthMember authMember = authMember();
            MemberDeleteRequestDto request = new MemberDeleteRequestDto("wrongPassword", PASSWORD);

            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(member));
            given(passwordEncoder.matches("wrongPassword", ENCODED_PASSWORD)).willReturn(false);

            // when & then
            assertThatThrownBy(() -> memberService.deleteMember(authMember, request))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.BAD_REQUEST)
                    .hasMessage("잘못된 비밀번호입니다.");
        }

    }



}
