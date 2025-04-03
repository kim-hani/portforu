package org.pinggu.portforu.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

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

    public SignupResponseDto(String bearerToken, Long id, String email, String name, String phoneNumber, String address, String userRole) {
        this.bearerToken = bearerToken;
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.userRole = userRole;
    }
}
