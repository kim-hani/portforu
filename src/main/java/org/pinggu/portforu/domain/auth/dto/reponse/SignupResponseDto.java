package org.pinggu.portforu.domain.auth.dto.reponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupResponseDto {

    private final Long id;
    private final String email;
    private final String userRole;

    public SignupResponseDto(Long id, String email, String userRole) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
    }
}
