package org.pinggu.portforu.domain.member.service;

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
import org.pinggu.portforu.domain.member.dto.request.PasswordUpdateRequestDto;
import org.pinggu.portforu.domain.member.dto.response.MemberResponseDto;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.pinggu.portforu.domain.member.repository.MemberRepository;
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
    private AuthMember authMember;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .email(EMAIL)
                .password(ENCODED_PASSWORD)
                .name(NAME)
                .phoneNumber(PHONE)
                .address(ADDRESS)
                .userRole(UserRole.ROLE_USER)
                .build();
        ReflectionTestUtils.setField(member, "id", MEMBER_ID);
        ReflectionTestUtils.setField(member, "viewCount", 3);

        authMember = new AuthMember(MEMBER_ID, EMAIL, NAME, PHONE, ADDRESS, UserRole.ROLE_USER);
    }

    @Nested
    class 회원_조회 {
        @Test
        void 회원_조회_성공() {
            // given
            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(member));

            // when
            MemberResponseDto response = memberService.findMember(authMember, MEMBER_ID);

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
            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> memberService.findMember(authMember, MEMBER_ID))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND)
                    .hasMessage("존재하지 않는 회원정보입니다.");
        }

        @Test
        void 다른_회원_정보_조회_시_권한_에러_발생() {
            // given
            Long otherMemberId = 2L;

            // when & then
            assertThatThrownBy(() -> memberService.findMember(authMember, otherMemberId))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.FORBIDDEN)
                    .hasMessage("접근 권한이 없습니다");

            then(memberRepository).should(never()).findById(otherMemberId);
        }
    }

    @Nested
    class 회원_정보_수정 {
        @Test
        void 회원_정보_수정_성공() {
            // given
            MemberUpdateRequestDto request = new MemberUpdateRequestDto(
                    "nameUpdate",
                    "010-1111-2222",
                    "유미시 유미구"
            );

            given(memberRepository.updateMemberInfo(
                    MEMBER_ID, request.getName(), request.getPhoneNumber(), request.getAddress()
            )).willReturn(1);

            Member updatedMember = Member.builder()
                    .email(EMAIL)
                    .password(ENCODED_PASSWORD)
                    .name("nameUpdate")
                    .phoneNumber("010-1111-2222")
                    .address("유미시 유미구")
                    .userRole(UserRole.ROLE_USER)
                    .build();
            ReflectionTestUtils.setField(updatedMember, "id", MEMBER_ID);

            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(updatedMember));

            // when
            MemberResponseDto response = memberService.updateMember(authMember, MEMBER_ID, request);

            // then
            assertThat(response)
                    .isNotNull()
                    .extracting(
                            MemberResponseDto::getName,
                            MemberResponseDto::getPhoneNumber,
                            MemberResponseDto::getAddress
                    )
                    .containsExactly("nameUpdate", "010-1111-2222", "유미시 유미구");

            then(memberRepository).should().updateMemberInfo(
                    MEMBER_ID, request.getName(), request.getPhoneNumber(), request.getAddress()
            );
        }

        @Test
        void 다른_회원_정보_수정_시_권한_에러_발생() {
            // given
            Long otherMemberId = 2L;
            MemberUpdateRequestDto request = new MemberUpdateRequestDto(
                    "nameUpdate",
                    "010-1111-2222",
                    "유미시 유미구"
            );

            // when & then
            assertThatThrownBy(() -> memberService.updateMember(authMember, otherMemberId, request))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.FORBIDDEN)
                    .hasMessage("접근 권한이 없습니다");

            then(memberRepository).should(never()).updateMemberInfo(anyLong(), anyString(), anyString(), anyString());
        }

        @Test
        void 수정_사항이_없을_경우_에러_발생() {
            // given
            MemberUpdateRequestDto request = new MemberUpdateRequestDto(
                    "nameUpdate",
                    "010-1111-2222",
                    "유미시 유미구"
            );

            given(memberRepository.updateMemberInfo(MEMBER_ID, request.getName(), request.getPhoneNumber(), request.getAddress()))
                    .willReturn(0);

            // when & then
            assertThatThrownBy(() -> memberService.updateMember(authMember, MEMBER_ID, request))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_MODIFIED)
                    .hasMessage("수정 사항이 없습니다.");
        }
    }

    @Nested
    class 비밀번호_변경 {
        @Test
        void 비밀번호_변경_성공() {
            // given
            PasswordUpdateRequestDto request = new PasswordUpdateRequestDto(
                    PASSWORD, "newPassword1!"
            );

            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(member));
            given(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).willReturn(true);
            given(passwordEncoder.matches(request.getNewPassword(), ENCODED_PASSWORD)).willReturn(false);
            given(passwordEncoder.encode(request.getNewPassword())).willReturn("newEncodedPassword");
            given(memberRepository.updatePassword(MEMBER_ID, "newEncodedPassword")).willReturn(1);

            Member updatedMember = Member.builder()
                    .email(EMAIL)
                    .password("newEncodedPassword")
                    .name(NAME)
                    .phoneNumber(PHONE)
                    .address(ADDRESS)
                    .userRole(UserRole.ROLE_USER)
                    .build();
            ReflectionTestUtils.setField(updatedMember, "id", MEMBER_ID);

            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(updatedMember));

            // when
            MemberResponseDto response = memberService.updatePassword(authMember, MEMBER_ID, request);

            // then
            assertThat(response).isNotNull();
            then(memberRepository).should().updatePassword(MEMBER_ID, "newEncodedPassword");
        }

        @Test
        void 다른_회원_비밀번호_변경_시_권한_에러_발생() {
            // given
            Long otherMemberId = 2L;
            PasswordUpdateRequestDto request = new PasswordUpdateRequestDto(
                    PASSWORD,
                    "newPassword1!"
            );

            // when & then
            assertThatThrownBy(() -> memberService.updatePassword(authMember, otherMemberId, request))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.FORBIDDEN)
                    .hasMessage("접근 권한이 없습니다");

            then(memberRepository).should(never()).updatePassword(anyLong(), anyString());
        }

        @Test
        void 기존_비밀번호_불일치_시_에러_발생() {
            // given
            PasswordUpdateRequestDto request = new PasswordUpdateRequestDto(
                    "wrongPassword",
                    "newPassword1!"
            );

            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(member));
            given(passwordEncoder.matches("wrongPassword", ENCODED_PASSWORD)).willReturn(false);

            // when & then
            assertThatThrownBy(() -> memberService.updatePassword(authMember, MEMBER_ID, request))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.BAD_REQUEST)
                    .hasMessage("기존 비밀번호가 일치하지 않습니다.");
        }

        @Test
        void 동일한_비밀번호로_변경_시_에러_발생() {
            // given
            PasswordUpdateRequestDto request = new PasswordUpdateRequestDto(
                    PASSWORD,
                    "samePassword1!"
            );

            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(member));
            given(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).willReturn(true);
            given(passwordEncoder.matches(request.getNewPassword(), ENCODED_PASSWORD)).willReturn(true);

            // when & then
            assertThatThrownBy(() -> memberService.updatePassword(authMember, MEMBER_ID, request))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.BAD_REQUEST)
                    .hasMessage("동일한 비밀번호로 변경할수 없습니다.");
        }

        @Test
        void 비밀번호_변경_실패_시_에러_발생() {
            // given
            PasswordUpdateRequestDto request = new PasswordUpdateRequestDto(
                    PASSWORD,
                    "newPassword1!"
            );

            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(member));
            given(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).willReturn(true);
            given(passwordEncoder.matches(request.getNewPassword(), ENCODED_PASSWORD)).willReturn(false);
            given(passwordEncoder.encode(request.getNewPassword())).willReturn("newEncodedPassword");
            given(memberRepository.updatePassword(MEMBER_ID, "newEncodedPassword")).willReturn(0);

            // when & then
            assertThatThrownBy(() -> memberService.updatePassword(authMember, MEMBER_ID, request))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_MODIFIED)
                    .hasMessage("비밀번호 변경에 실패했습니다.");
        }
    }

    @Nested
    class 회원_삭제 {
        @Test
        void 회원_삭제_성공() {
            // given
            MemberDeleteRequestDto request = new MemberDeleteRequestDto(PASSWORD, PASSWORD);

            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(member));
            given(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).willReturn(true);

            ReflectionTestUtils.setField(member, "id", MEMBER_ID);

            // When
            Long deletedMemberId = memberService.deleteMember(authMember, MEMBER_ID, request);

            // Then
            assertThat(deletedMemberId).isEqualTo(MEMBER_ID);
            then(memberRepository).should().findById(MEMBER_ID);
        }

        @Test
        void 다른_회원_삭제_시_권한_에러_발생() {
            // given
            Long otherMemberId = 2L;
            MemberDeleteRequestDto request = new MemberDeleteRequestDto(PASSWORD, PASSWORD);

            // when & then
            assertThatThrownBy(() -> memberService.deleteMember(authMember, otherMemberId, request))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.FORBIDDEN)
                    .hasMessage("접근 권한이 없습니다");

            then(memberRepository).should(never()).findById(otherMemberId);
        }

        @Test
        void 회원_삭제_중_비밀번호_불일치_시_에러_발생() {
            // given
            MemberDeleteRequestDto request = new MemberDeleteRequestDto("wrongPassword", PASSWORD);

            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(member));
            given(passwordEncoder.matches("wrongPassword", ENCODED_PASSWORD)).willReturn(false);

            // when & then
            assertThatThrownBy(() -> memberService.deleteMember(authMember, MEMBER_ID, request))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.BAD_REQUEST)
                    .hasMessage("잘못된 비밀번호입니다.");
        }

        @Test
        void 회원_삭제_중_비밀번호_확인_불일치_시_에러_발생() {
            // given
            MemberDeleteRequestDto request = new MemberDeleteRequestDto(PASSWORD, "differentPassword");

            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(member));
            given(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).willReturn(true);

            // when & then
            assertThatThrownBy(() -> memberService.deleteMember(authMember, MEMBER_ID, request))
                    .isInstanceOf(CustomException.class)
                    .hasFieldOrPropertyWithValue("status", HttpStatus.BAD_REQUEST)
                    .hasMessage("비밀번호 확인에 실패했습니다.");
        }

    }



}
