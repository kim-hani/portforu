package org.pinggu.portforu.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pinggu.portforu.domain.member.entity.Member;

@Getter
@Builder
public class SignupResponseDto {

    private final String bearerToken;

    private final Long id;
    private final String email;
    private final String name;
    private final String phoneNumber;
    private final String address;
    private final String userRole;

    public static SignupResponseDto from(Member member, String bearerToken) {
        return SignupResponseDto.builder()
                .bearerToken(bearerToken)
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .userRole(member.getUserRole().name())
                .build();
    }

}
