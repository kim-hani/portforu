package org.pinggu.portforu.domain.membership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.pinggu.portforu.common.domain.Pagecond;
import org.pinggu.portforu.config.JwtUtil;
import org.pinggu.portforu.config.SecurityConfig;
import org.pinggu.portforu.domain.membership.dto.request.MembershipSaveRequestDto;
import org.pinggu.portforu.domain.membership.dto.request.MembershipUpdateRequestDto;
import org.pinggu.portforu.domain.membership.dto.response.MembershipResponseDto;
import org.pinggu.portforu.domain.membership.repository.MembershipRepository;
import org.pinggu.portforu.domain.membership.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MembershipController.class)
@Import({SecurityConfig.class, JwtUtil.class})
@WithMockUser
class MembershipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private MembershipService membershipService;

    @MockitoBean
    private MembershipRepository membershipRepository;

    @Test
    public void 맴버쉽_생성_컨트롤러_테스트() throws Exception {
        // given
        MembershipSaveRequestDto request = new MembershipSaveRequestDto("테스트", 10000, 2000, 2025);
        MembershipResponseDto membershipResponse = MembershipResponseDto.builder()
                .id(1L)
                .name("테스트")
                .price(10000)
                .quantity(2000)
                .year(2025)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .deletedAt(null)
                .build();

        given(membershipService.saveMembership(any(MembershipSaveRequestDto.class)))
                .willReturn(membershipResponse);

        // when & then
        mockMvc.perform(post("/api/v1/memberships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 모든_회원조회_컨트롤러_테스트() throws Exception {
        // given
        MembershipResponseDto membership1 = MembershipResponseDto.builder()
                .id(1L)
                .name("회원1")
                .price(10000)
                .quantity(2000)
                .year(2025)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .deletedAt(null)
                .build();

        MembershipResponseDto membership2 = MembershipResponseDto.builder()
                .id(2L)
                .name("회원2")
                .price(15000)
                .quantity(1500)
                .year(2023)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .deletedAt(null)
                .build();

        List<MembershipResponseDto> membershipsList = Arrays.asList(membership1, membership2);
        Page<MembershipResponseDto> memberships = new PageImpl<>(membershipsList, PageRequest.of(0, 10), membershipsList.size());

        given(membershipService.findAllMemberships(any(Pagecond.class)))
                .willReturn(memberships);

        // when & then
        mockMvc.perform(get("/api/v1/memberships")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 특정회원_조회_컨트롤러_테스트() throws Exception {
        // given
        Long membershipId = 1L;
        MembershipResponseDto membershipResponse = MembershipResponseDto.builder()
                .id(membershipId)
                .name("회원1")
                .price(10000)
                .quantity(2000)
                .year(2024)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .deletedAt(null)
                .build();

        given(membershipService.findMembershipById(eq(membershipId)))
                .willReturn(membershipResponse);

        // when & then
        mockMvc.perform(get("/api/v1/memberships/{membershipId}", membershipId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 회원정보_업데이트_컨트롤러_테스트() throws Exception {
        // given
        Long membershipId = 1L;
        MembershipUpdateRequestDto request = new MembershipUpdateRequestDto("업데이트된 회원", 12000, 2500, 2025);
        MembershipResponseDto updatedMembership = MembershipResponseDto.builder()
                .id(membershipId)
                .name("업데이트된 회원")
                .price(12000)
                .quantity(2500)
                .year(2025)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .deletedAt(null)
                .build();

        given(membershipService.updateMembership(eq(membershipId), any(MembershipUpdateRequestDto.class)))
                .willReturn(updatedMembership);

        // when & then
        mockMvc.perform(put("/api/v1/memberships/{membershipId}", membershipId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 회원정보_삭제_컨트롤러_테스트() throws Exception {
        // given
        Long membershipId = 1L;
        MembershipResponseDto deletedMembership = MembershipResponseDto.builder()
                .id(membershipId)
                .name("삭제된 회원")
                .price(10000)
                .quantity(2000)
                .year(2024)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .deletedAt(Instant.now())
                .build();

        given(membershipService.deleteMembership(eq(membershipId)))
                .willReturn(deletedMembership);

        // when & then
        mockMvc.perform(delete("/api/v1/memberships/{membershipId}", membershipId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}