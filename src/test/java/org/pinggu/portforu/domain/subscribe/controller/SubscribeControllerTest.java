package org.pinggu.portforu.domain.subscribe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.config.JwtUtil;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.pinggu.portforu.domain.payment.entity.Payment;
import org.pinggu.portforu.domain.subscribe.dto.request.SubscribeRequestDto;
import org.pinggu.portforu.domain.subscribe.dto.response.SubscribeResponseDto;
import org.pinggu.portforu.domain.subscribe.service.SubscribeService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubscribeController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SuppressWarnings("deprecation")
public class SubscribeControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private SubscribeService subscribeService;

    @MockBean
    private JwtUtil jwtUtil;

    // 생성자 주입: @TestConstructor(autowireMode = ALL)와 @TestInstance(PER_CLASS) 덕분에 스프링 컨텍스트에서 자동 주입됨.
    public SubscribeControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, SubscribeService subscribeService, JwtUtil jwtUtil) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.subscribeService = subscribeService;
        this.jwtUtil = jwtUtil;
    }

    @Test
    void 구독_생성_성공() throws Exception {
        // Given
        Long membershipId = 1L;
        SubscribeRequestDto requestDto = SubscribeRequestDto.builder()
                .paymentMethod(Payment.PaymentMethod.CREDIT_CARD)
                .build();

        SubscribeResponseDto responseDto = SubscribeResponseDto.builder()
                .id(1L)
                .memberId(1L)
                .membershipId(1L)
                .paymentMethod("CREDIT_CARD")
                .paymentId(1L)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .active(true)
                .build();

        Mockito.when(subscribeService.saveSubscribe(anyLong(), anyLong(), any(SubscribeRequestDto.class)))
                .thenReturn(responseDto);

        // Given: 테스트용 AuthMember와 인증 토큰 생성
        AuthMember authMember = new AuthMember(
                1L,
                "test@example.com",
                "Test User",
                "010-1234-5678",
                "Some Address",
                UserRole.ROLE_USER
        );
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(authMember, null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER")));

        // When & Then
        mockMvc.perform(post("/api/v1/memberships/{membershipId}/subscribes", membershipId)
                        .with(csrf())
                        .with(authentication(authToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.paymentMethod").value("CREDIT_CARD"));
    }

    @Test
    void 구독_목록_조회_성공() throws Exception {
        // Given
        AuthMember authMember = new AuthMember(
                1L,
                "test@example.com",
                "Test User",         // name
                "010-1234-5678",      // phoneNumber
                "Some Address",       // address
                UserRole.ROLE_USER
        );
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(authMember, null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER")));

        // Given: 구독 목록 조회 응답 데이터 생성 (Page 객체 사용)
        SubscribeResponseDto responseDto = SubscribeResponseDto.builder()
                .id(1L)
                .memberId(1L)
                .membershipId(1L)
                .paymentMethod("CREDIT_CARD")
                .paymentId(1L)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .active(true)
                .build();
        List<SubscribeResponseDto> list = Collections.singletonList(responseDto);
        Page<SubscribeResponseDto> page = new PageImpl<>(list);

        Mockito.when(subscribeService.findSubscribes(anyLong(), any(Pageable.class)))
                .thenReturn(page);

        // When & Then: GET 요청에 인증 정보를 추가하고, 응답 JSON의 값 검증
        mockMvc.perform(get("/api/v1/my/subscribes")
                        .with(authentication(authToken))
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.page.totalElement").value(1));
    }

    @Test
    void 구독_삭제_성공() throws Exception {
        // Given
        Long memberId = 1L;
        Long subscribeId = 1L;
        Mockito.doNothing().when(subscribeService).deleteSubscribe(memberId,subscribeId);

        AuthMember authMember = new AuthMember(
                1L,
                "test@example.com",
                "Test User",
                "010-1234-5678",
                "Some Address",
                UserRole.ROLE_USER
        );
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(authMember, null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER")));

        // When & Then: CSRF 토큰과 인증 정보를 추가하여 DELETE 요청을 보내고 응답 검증
        mockMvc.perform(delete("/api/v1/my/subscribes/{subscribeId}", subscribeId)
                        .with(csrf())
                        .with(authentication(authToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("구독이 취소되었습니다."));
    }
}
