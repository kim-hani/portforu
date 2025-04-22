package org.pinggu.portforu.domain.membership.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.membership.dto.request.MembershipSaveRequestDto;
import org.pinggu.portforu.domain.membership.dto.request.MembershipUpdateRequestDto;
import org.pinggu.portforu.domain.membership.dto.response.MembershipResponseDto;
import org.pinggu.portforu.domain.membership.entity.Membership;
import org.pinggu.portforu.domain.membership.repository.MembershipRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MembershipServiceTest {

    @Mock
    private MembershipRepository membershipRepository;

    @Mock
    private MembershipFinder membershipFinder;

    @InjectMocks
    private MembershipService membershipService;

    @Nested
    class CreateMembershipTest {
        @Test
        void 맴버십_생성_성공() {
            // given
            MembershipSaveRequestDto requestDto = new MembershipSaveRequestDto("이름", 2000, 10, 2020);
            Membership savedMembership = new Membership("이름", 2000, 10, 2020);

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
        MembershipSaveRequestDto requestDto = new MembershipSaveRequestDto("이름", 2000, 10, 2020);
        given(membershipRepository.save(any())).willThrow(new RuntimeException("저장 실패"));

        // when, then
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                membershipService.saveMembership(requestDto)
        );
        assertEquals("저장 실패", ex.getMessage());
    }

    @Test
    void 모든_맴버십_조회_성공() {
        // given
        List<Membership> memberships = List.of(
                new Membership("이름1", 2000, 10, 2020),
                new Membership("이름2", 3000, 5, 2021)
        );

        given(membershipRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))).willReturn(memberships);

        // when
        List<MembershipResponseDto> response = membershipService.findAllMemberships();

        // then
        assertEquals(2, response.size());
        assertEquals("이름1", response.get(0).getName());
        assertEquals("이름2", response.get(1).getName());
    }

    @Test
    void 모든_맴버십_조회_실패() {
        // given
        given(membershipRepository.findAll(any(Sort.class))).willThrow(new RuntimeException("조회 실패"));

        // when, then
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                membershipService.findAllMemberships()
        );
        assertEquals("조회 실패", ex.getMessage());
    }

    @Test
    void 맴버십_단건_조회_성공() {
        // given
        Long id = 1L;
        Membership membership = new Membership("이름1", 2000, 10, 2020);
        setId(membership, id);

        given(membershipFinder.findById(id)).willReturn(membership);

        // when
        MembershipResponseDto response = membershipService.findMembershipById(id);

        // then
        assertEquals("이름1", response.getName());
        assertEquals(id, response.getId());
    }

    @Test
    void 맴버십_단건_조회_실패() {
        // given
        Long id = 1L;
        given(membershipFinder.findById(id)).willThrow(new CustomException(HttpStatus.BAD_REQUEST, "아이디가 없습니다."));

        // when, then
        CustomException ex = assertThrows(CustomException.class, () -> membershipService.findMembershipById(id));
        assertEquals("아이디가 없습니다.", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void 맴버십_업데이트_성공() {
        // given
        Long id = 1L;
        Membership membership = new Membership("이름1", 2000, 10, 2020);
        setId(membership, id);

        MembershipUpdateRequestDto request = new MembershipUpdateRequestDto("새로운 이름", 2500, 15, 2021);

        given(membershipFinder.findById(id)).willReturn(membership);

        // when
        membershipService.updateMembership(id, request);

        // then
        assertEquals("새로운 이름", membership.getName());
        assertEquals(2500, membership.getPrice());
        assertEquals(15, membership.getQuantity());
        assertEquals(2021, membership.getYear());
    }

    @Test
    void 맴버십_업데이트_실패() {
        // given
        Long id = 1L;
        MembershipUpdateRequestDto request = new MembershipUpdateRequestDto("새로운 이름", 2500, 15, 2021);

        given(membershipFinder.findById(id)).willThrow(new CustomException(HttpStatus.BAD_REQUEST, "아이디가 없습니다."));

        // when, then
        CustomException ex = assertThrows(CustomException.class, () -> membershipService.updateMembership(id, request));
        assertEquals("아이디가 없습니다.", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void 맴버십_삭제_성공() {
        // given
        Long id = 1L;
        Membership membership = new Membership("이름1", 2000, 10, 2020);
        setId(membership, id);

        given(membershipFinder.findById(id)).willReturn(membership);

        // when
        Long deletedId = membershipService.deleteMembership(id);

        // then
        assertEquals(id, deletedId);
    }

    @Test
    void 맴버십_삭제_실패() {
        // given
        Long id = 1L;
        given(membershipFinder.findById(id)).willThrow(new CustomException(HttpStatus.BAD_REQUEST, "아이디가 없습니다."));

        // when, then
        CustomException ex = assertThrows(CustomException.class, () -> membershipService.deleteMembership(id));
        assertEquals("아이디가 없습니다.", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    // 리플렉션 유틸 메서드
    private void setId(Membership membership, Long id) {
        try {
            Field idField = Membership.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(membership, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
