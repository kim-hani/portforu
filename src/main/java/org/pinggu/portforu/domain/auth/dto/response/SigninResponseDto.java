package org.pinggu.portforu.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class SigninResponseDto {

    private final String accessToken;
    private final String refreshToken;

    public SigninResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
