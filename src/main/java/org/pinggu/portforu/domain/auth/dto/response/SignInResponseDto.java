package org.pinggu.portforu.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class SignInResponseDto {

    private final String accessToken;
    private final String refreshToken;

    public SignInResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
