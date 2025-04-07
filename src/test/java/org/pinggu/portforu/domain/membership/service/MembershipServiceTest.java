package org.pinggu.portforu.domain.membership.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pinggu.portforu.common.domain.Pagecond;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.membership.dto.request.MembershipSaveRequestDto;
import org.pinggu.portforu.domain.membership.dto.request.MembershipUpdateRequestDto;
import org.pinggu.portforu.domain.membership.dto.response.MembershipResponseDto;
import org.pinggu.portforu.domain.membership.entity.Membership;
import org.pinggu.portforu.domain.membership.repository.MembershipRepository;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MembershipServiceTest {

    @Mock
    private MembershipRepository membershipRepository;

    @InjectMocks
    private MembershipService membershipService;

    @Nested
    class CreateMembershipTest {
        @Test
        void 맴버십_생성_성공() {
            // given
            MembershipSaveRequestDto requestDto = new MembershipSaveRequestDto(
                    "이름",
                    2000,
                    10,
                    2020
            );

            Membership savedMembership = new Membership(
                    "이름",
                    2000,
                    10,
                    2020
            );

            given(membershipRepository.save(any(Membership.class))).willReturn(savedMembership);

            // when
            MembershipResponseDto responseDto = membershipService.saveMembership(requestDto);

            // then
            assertEquals("이름", responseDto.getName());
            assertEquals(2000, responseDto.getPrice());
            assertEquals(10, responseDto.getQuantity());
            assertEquals(2020, responseDto.getYear());
        }
    }

    @Test
    void 맴버십_생성_실패() {
        // given
        MembershipSaveRequestDto requestDto = new MembershipSaveRequestDto(
                "이름",
                2000,
                10,
                2020
        );

        // 예외
        given(membershipRepository.save(any(Membership.class))).willThrow(new RuntimeException("저장 실패"));

        // when, then
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                membershipService.saveMembership(requestDto)
        );

        assertEquals("저장 실패", exception.getMessage());
    }

    void 모든_맴버십_조회_성공() {
        // given
        Pagecond pagecond = new Pagecond(1, 10);
        Pageable pageable = PageRequest.of(pagecond.getPageNum() - 1, pagecond.getPageSize());

        List<Membership> memberships = List.of(
                Membership.builder()
                        .name("이름1")
                        .price(2000)
                        .quantity(10)
                        .year(2020)
                        .build(),
                Membership.builder()
                        .name("이름2")
                        .price(3000)
                        .quantity(5)
                        .year(2021)
                        .build()
        );

        Page<Membership> membershipPage = new PageImpl<>(memberships, pageable, memberships.size());

        given(membershipRepository.findAll(pageable)).willReturn(membershipPage);

        // when
        Page<MembershipResponseDto> response = membershipService.findAllMemberships(pagecond);

        // then
        assertEquals(2, response.getTotalElements());
        assertEquals("이름1", response.getContent().get(0).getName());
        assertEquals("이름2", response.getContent().get(1).getName());
    }

    @Test
    void 모든_맴버십_조회_실패() {
        // given
        Pagecond pagecond = new Pagecond(1, 10);
        Pageable pageable = PageRequest.of(pagecond.getPageNum() - 1, pagecond.getPageSize(), Sort.by(Sort.Order.desc("createdAt")));

        // 예외 발생
        given(membershipRepository.findAll(pageable)).willThrow(new RuntimeException("조회 실패"));

        // when, then
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                membershipService.findAllMemberships(pagecond)
        );

        assertEquals("조회 실패", exception.getMessage());
    }

    @Test
    void 맴버십_단건_조회_성공() {
        // given
        Long membershipId = 1L;

        Membership membership = Membership.builder()
                .name("이름1")
                .price(2000)
                .quantity(10)
                .year(2020)
                .build();

        try {
            Field idField = Membership.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(membership, membershipId);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        given(membershipRepository.findById(membershipId)).willReturn(Optional.of(membership));

        // when
        MembershipResponseDto responseDto = membershipService.findMembershipById(membershipId);

        // then
        assertEquals(membershipId, responseDto.getId());
        assertEquals("이름1", responseDto.getName());
        assertEquals(2000, responseDto.getPrice());
        assertEquals(10, responseDto.getQuantity());
        assertEquals(2020, responseDto.getYear());
    }

    @Test
    void 맴버십_단건_조회_실패_아이디없음() {
        // given
        Long membershipId = 1L;

        // ID로 멤버십을 찾을 수 없을 때 예외
        given(membershipRepository.findById(membershipId)).willReturn(Optional.empty());

        // when, then
        CustomException exception = assertThrows(CustomException.class, () ->
                membershipService.findMembershipById(membershipId)
        );

        assertEquals("아이디가 없습니다.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void 맴버십_업데이트_성공() {
        // given
        Long membershipId = 1L;

        Membership membership = Membership.builder()
                .name("이름1")
                .price(2000)
                .quantity(10)
                .year(2020)
                .build();

        try {
            Field idField = Membership.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(membership, membershipId);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        MembershipUpdateRequestDto request = new MembershipUpdateRequestDto("새로운 이름", 2500, 15, 2021);

        given(membershipRepository.findById(membershipId)).willReturn(Optional.of(membership));
        given(membershipRepository.save(any(Membership.class))).willReturn(membership);

        // when
        MembershipResponseDto responseDto = membershipService.updateMembership(membershipId, request);

        // then
        assertEquals(membershipId, responseDto.getId());
        assertEquals("새로운 이름", responseDto.getName());
        assertEquals(2500, responseDto.getPrice());
        assertEquals(15, responseDto.getQuantity());
        assertEquals(2021, responseDto.getYear());
    }

    @Test
    void 맴버십_업데이트_실패_아이디없음() {
        // given
        Long membershipId = 1L;
        MembershipUpdateRequestDto request = new MembershipUpdateRequestDto("새로운 이름", 2500, 15, 2021);

        // ID로 멤버십을 찾을 수 없을 때 예외
        given(membershipRepository.findById(membershipId)).willReturn(Optional.empty());

        // when, then
        CustomException exception = assertThrows(CustomException.class, () ->
                membershipService.updateMembership(membershipId, request)
        );

        assertEquals("아이디가 없습니다.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void 맴버십_삭제_성공() {
        // given
        Long membershipId = 1L;

        Membership membership = Membership.builder()
                .name("이름1")
                .price(2000)
                .quantity(10)
                .year(2020)
                .build();

        try {
            Field idField = Membership.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(membership, membershipId);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        given(membershipRepository.findById(membershipId)).willReturn(Optional.of(membership));
        given(membershipRepository.save(any(Membership.class))).willReturn(membership);

        // when
        MembershipResponseDto responseDto = membershipService.deleteMembership(membershipId);

        // then
        assertEquals(membershipId, responseDto.getId());
        assertNotNull(responseDto.getDeletedAt());
    }

    @Test
    void 맴버십_삭제_실패_아이디없음() {
        // given
        Long membershipId = 1L;

        // ID로 멤버십을 찾을 수 없을 때 예외
        given(membershipRepository.findById(membershipId)).willReturn(Optional.empty());

        // when, then
        CustomException exception = assertThrows(CustomException.class, () ->
                membershipService.deleteMembership(membershipId)
        );

        assertEquals("아이디가 없습니다.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}