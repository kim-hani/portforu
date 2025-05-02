//package org.pinggu.portforu.domain.membership.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.pinggu.portforu.config.JwtUtil;
//import org.pinggu.portforu.config.SecurityConfig;
//import org.pinggu.portforu.domain.membership.dto.request.MembershipSaveRequestDto;
//import org.pinggu.portforu.domain.membership.dto.request.MembershipUpdateRequestDto;
//import org.pinggu.portforu.domain.membership.dto.response.MembershipResponseDto;
//import org.pinggu.portforu.domain.membership.service.MembershipService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.Instant;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.BDDMockito.willDoNothing;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest({MembershipController.class, MembershipAdminController.class})
//@Import({SecurityConfig.class, JwtUtil.class})
//@WithMockUser
//class MembershipControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockitoBean
//    private JwtUtil jwtUtil;
//
//    @MockitoBean
//    private MembershipService membershipService;
//
//    @Test
//    public void 맴버쉽_생성_컨트롤러_테스트() throws Exception {
//        // given
//        MembershipSaveRequestDto request = new MembershipSaveRequestDto("테스트", 10000, 2000, 2025);
//        MembershipResponseDto membershipResponse = MembershipResponseDto.builder()
//                .id(1L)
//                .name("테스트")
//                .price(10000)
//                .quantity(2000)
//                .year(2025)
//                .createdAt(Instant.now())
//                .updatedAt(Instant.now())
//                .isDeleted(false)
//                .build();
//
//        given(membershipService.saveMembership(any(MembershipSaveRequestDto.class)))
//                .willReturn(membershipResponse);
//
//        // when & then
//        mockMvc.perform(post("/api/v1/memberships")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.name").value("테스트"));
//    }
//
//    @Test
//    public void 모든_회원조회_컨트롤러_테스트() throws Exception {
//        // given
//        List<MembershipResponseDto> memberships = List.of(
//                MembershipResponseDto.builder()
//                        .id(1L).name("회원1").price(10000).quantity(2000).year(2025)
//                        .createdAt(Instant.now()).updatedAt(Instant.now()).isDeleted(false).build(),
//                MembershipResponseDto.builder()
//                        .id(2L).name("회원2").price(15000).quantity(1500).year(2023)
//                        .createdAt(Instant.now()).updatedAt(Instant.now()).isDeleted(false).build()
//        );
//
//        given(membershipService.findAllMemberships()).willReturn(memberships);
//
//        // when & then
//        mockMvc.perform(get("/api/v1/memberships")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.length()").value(2));
//    }
//
//    @Test
//    public void 특정회원_조회_컨트롤러_테스트() throws Exception {
//        // given
//        Long membershipId = 1L;
//        MembershipResponseDto membershipResponse = MembershipResponseDto.builder()
//                .id(membershipId)
//                .name("회원1")
//                .price(10000)
//                .quantity(2000)
//                .year(2024)
//                .createdAt(Instant.now())
//                .updatedAt(Instant.now())
//                .isDeleted(false)
//                .build();
//
//        given(membershipService.findMembershipById(eq(membershipId)))
//                .willReturn(membershipResponse);
//
//        // when & then
//        mockMvc.perform(get("/api/v1/memberships/{membershipId}", membershipId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.name").value("회원1"));
//    }
//
//    @Test
//    public void 회원정보_업데이트_컨트롤러_테스트() throws Exception {
//        // given
//        Long membershipId = 1L;
//        MembershipUpdateRequestDto request = new MembershipUpdateRequestDto("업데이트된 회원", 12000, 2500, 2025);
//
//        willDoNothing().given(membershipService).updateMembership(eq(membershipId), any(MembershipUpdateRequestDto.class));
//
//        // when & then
//        mockMvc.perform(put("/api/v1/memberships/{membershipId}", membershipId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("수정되었습니다"));
//    }
//
//    @Test
//    public void 회원정보_삭제_컨트롤러_테스트() throws Exception {
//        // given
//        Long membershipId = 1L;
//        given(membershipService.deleteMembership(eq(membershipId))).willReturn(membershipId);
//
//        // when & then
//        mockMvc.perform(delete("/api/v1/memberships/{membershipId}", membershipId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data").value(membershipId));
//    }
//}
