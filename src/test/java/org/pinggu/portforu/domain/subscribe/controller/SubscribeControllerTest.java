package org.pinggu.portforu.domain.subscribe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.config.JwtUtil;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.pinggu.portforu.domain.subscribe.dto.response.SubscribeResponseDto;
import org.pinggu.portforu.domain.subscribe.enums.SubscribeStatus;
import org.pinggu.portforu.domain.subscribe.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.Instant;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubscribeController.class)
class SubscribeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SubscribeService subscribeService;

    @MockBean
    private JwtUtil jwtUtil;

    private AuthMember makeAuthMember() {
        return new AuthMember(
                1L,
                "test@pinggu.com",
                "테스터",
                "010-0000-0000",
                "서울시",
                UserRole.ROLE_USER,
                "local"
        );
    }

    @Test
    void 구독요청_성공() throws Exception {
        Long membershipId = 42L;
        AuthMember auth = makeAuthMember();

        // 1) DTO 생성
        SubscribeResponseDto dto = SubscribeResponseDto.builder()
                .id(100L)
                .memberId(auth.getId())
                .membershipId(membershipId)
                .paymentMethod("CARD")
                .paymentId(200L)
                .startDate(Instant.now())
                .endDate(Instant.now().plusSeconds(3600))
                .active(true)
                .status(SubscribeStatus.PENDING.name())
                .build();

        // 2) stub 설정
        given(subscribeService.saveSubscribe(auth, membershipId))
                .willReturn(dto);

        // 3) perform 및 검증
        mockMvc.perform(post("/api/v1/subscribes/{membershipId}", membershipId)
                        .with(authentication(
                                new UsernamePasswordAuthenticationToken(auth, null, auth.getAuthorities())
                        ))
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(100))
                .andExpect(jsonPath("$.data.membershipId").value(membershipId));
    }

    @Test
    void 구독조회_성공() throws Exception {
        AuthMember auth = makeAuthMember();
        List<SubscribeResponseDto> list = List.of();

        given(subscribeService.findAllSubscribes(auth))
                .willReturn(list);

        mockMvc.perform(get("/api/v1/subscribes")
                        .with(authentication(
                                new UsernamePasswordAuthenticationToken(auth, null, auth.getAuthorities())
                        ))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void 구독취소_성공() throws Exception {
        Long subscribeId = 99L;
        AuthMember auth = makeAuthMember();

        given(subscribeService.deleteSubscribe(auth.getId(), subscribeId))
                .willReturn(subscribeId);

        mockMvc.perform(delete("/api/v1/subscribes/{subscribeId}", subscribeId)
                        .with(authentication(
                                new UsernamePasswordAuthenticationToken(auth, null, auth.getAuthorities())
                        ))
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(subscribeId));
    }
}

