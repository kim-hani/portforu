//package org.pinggu.portforu.domain.member.service;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.pinggu.portforu.common.domain.Pagecond;
//import org.pinggu.portforu.common.exception.CustomException;
//import org.pinggu.portforu.domain.member.dto.response.MemberResponseDto;
//import org.pinggu.portforu.domain.member.entity.Member;
//import org.pinggu.portforu.domain.member.enums.UserRole;
//import org.pinggu.portforu.domain.member.repository.MemberRepository;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpStatus;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.BDDMockito.then;
//
//
//@ExtendWith(MockitoExtension.class)
//public class AdminServiceTest {
//
//    @Mock
//    private MemberRepository memberRepository;
//
//    @InjectMocks
//    private AdminService adminService;
//
//    private static final String EMAIL = "yumi@example.com";
//    private static final String PASSWORD = "password!";
//    private static final String ENCODED_PASSWORD = "encodedPassword123!";
//    private static final String NAME = "yumi";
//    private static final String PHONE = "010-1234-0410";
//    private static final String ADDRESS = "고양시 야옹동";
//    private static final Long MEMBER_ID = 1L;
//
//    private Member member;
//
//    @BeforeEach
//    void setUp() {
//        member = Member.builder()
//                .email(EMAIL)
//                .password(ENCODED_PASSWORD)
//                .name(NAME)
//                .phoneNumber(PHONE)
//                .address(ADDRESS)
//                .userRole(UserRole.ROLE_ADMIN)
//                .build();
//        ReflectionTestUtils.setField(member, "id", MEMBER_ID);
//        ReflectionTestUtils.setField(member, "viewCount", 3);
//    }
//
//    @Nested
//    class 회원_조회 {
//        @Test
//        void 회원_조회_성공() {
//            // given
//            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(member));
//
//            // when
//            MemberResponseDto response = adminService.findMember(MEMBER_ID);
//
//            // then
//            assertThat(response)
//                    .isNotNull()
//                    .extracting(
//                            MemberResponseDto::getEmail,
//                            MemberResponseDto::getName,
//                            MemberResponseDto::getPhoneNumber,
//                            MemberResponseDto::getAddress
//                    )
//                    .containsExactly(EMAIL, NAME, PHONE, ADDRESS);
//
//            then(memberRepository).should().findById(MEMBER_ID);
//        }
//
//        @Test
//        void 존재하지_않는_회원_조회_시_에러_발생() {
//            // given
//            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.empty());
//
//            // when & then
//            assertThatThrownBy(() -> adminService.findMember(MEMBER_ID))
//                    .isInstanceOf(CustomException.class)
//                    .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND)
//                    .hasMessage("존재하지 않는 회원정보입니다.");
//        }
//    }
//
//    @Nested
//    class 모든_회원_조회 {
//        @Test
//        void 모든_회원_조회_성공() {
//            // given
//            Pagecond pagecond = new Pagecond(1, 10);
//            Pageable pageable = PageRequest.of(
//                    pagecond.getPageNum() - 1,
//                    pagecond.getPageSize(),
//                    Sort.by(Sort.Order.desc("createdAt"))
//            );
//
//            Member member1 = Member.builder()
//                    .email("yumi1@test.com")
//                    .password("password1")
//                    .name("yumi1")
//                    .phoneNumber("010-1111-1111")
//                    .address("경기도 유미시")
//                    .userRole(UserRole.ROLE_USER)
//                    .build();
//            ReflectionTestUtils.setField(member1, "id", 1L);
//
//            Member member2 = Member.builder()
//                    .email("yumi2@test.com")
//                    .password("password2")
//                    .name("yumi2")
//                    .phoneNumber("010-2222-2222")
//                    .address("경기도 유미시2")
//                    .userRole(UserRole.ROLE_USER)
//                    .build();
//            ReflectionTestUtils.setField(member2, "id", 2L);
//
//            List<Member> memberList = List.of(member1, member2);
//            Page<Member> memberPage = new PageImpl<>(memberList, pageable, memberList.size());
//
//            given(memberRepository.findAllByDeletedAtIsNull(pageable)).willReturn(memberPage);
//
//            // when
//            Page<MemberResponseDto> response = adminService.findAllMembers(pagecond);
//
//            // then
//            assertThat(response).isNotNull();
//            assertThat(response.getTotalElements()).isEqualTo(2);
//            assertThat(response.getContent().get(0).getEmail()).isEqualTo("yumi1@test.com");
//            assertThat(response.getContent().get(1).getEmail()).isEqualTo("yumi2@test.com");
//
//            then(memberRepository).should().findAllByDeletedAtIsNull(pageable);
//        }
//
//        @Test
//        void 회원이_없을_경우_빈_페이지_반환() {
//            // given
//            Pagecond pagecond = new Pagecond(1, 10);
//            Pageable pageable = PageRequest.of(
//                    pagecond.getPageNum() - 1,
//                    pagecond.getPageSize(),
//                    Sort.by(Sort.Order.desc("createdAt"))
//            );
//
//            List<Member> emptyList = List.of();
//            Page<Member> emptyPage = new PageImpl<>(emptyList, pageable, 0);
//
//            given(memberRepository.findAllByDeletedAtIsNull(pageable)).willReturn(emptyPage);
//
//            // when
//            Page<MemberResponseDto> response = adminService.findAllMembers(pagecond);
//
//            // then
//            assertThat(response).isNotNull();
//            assertThat(response.getTotalElements()).isEqualTo(0);
//            assertThat(response.getContent()).isEmpty();
//
//            then(memberRepository).should().findAllByDeletedAtIsNull(pageable);
//        }
//    }
//
//    @Nested
//    class 회원_삭제 {
//        @Test
//        void 회원_삭제_성공() {
//            // given
//            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(member));
//
//            ReflectionTestUtils.setField(member, "id", MEMBER_ID);
//
//            // when
//            Long deletedMemberId = adminService.deleteMember(MEMBER_ID);
//
//            // then
//            assertThat(deletedMemberId).isEqualTo(MEMBER_ID);
//            then(memberRepository).should().findById(MEMBER_ID);
//        }
//
//        @Test
//        void 존재하지_않는_회원_삭제_시_에러_발생() {
//            // given
//            given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.empty());
//
//            // when & then
//            assertThatThrownBy(() -> adminService.deleteMember(MEMBER_ID))
//                    .isInstanceOf(CustomException.class)
//                    .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND)
//                    .hasMessage("존재하지 않는 회원정보입니다.");
//        }
//    }
//}
