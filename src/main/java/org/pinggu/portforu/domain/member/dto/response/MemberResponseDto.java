package org.pinggu.portforu.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pinggu.portforu.domain.member.entity.Member;

@Getter
@Builder
public class MemberResponseDto {

    private final Long id;
    private final String email;
    private final String name;
    private final String phoneNumber;
    private final String address;
    private final String userRole;

    public static MemberResponseDto from(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .userRole(member.getUserRole().name())
                .build();
    }

}
