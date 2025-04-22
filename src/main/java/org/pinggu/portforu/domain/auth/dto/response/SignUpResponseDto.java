package org.pinggu.portforu.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pinggu.portforu.domain.member.entity.Member;

@Getter
@Builder
public class SignUpResponseDto {

    private final String accessToken;
    private final String refreshToken;

    private final Long id;
    private final String email;
    private final String name;
    private final String phoneNumber;
    private final String address;
    private final String userRole;

    public static SignUpResponseDto from(Member member, String accessToken, String refreshToken) {
        return SignUpResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .userRole(member.getUserRole().name())
                .build();
    }

}
