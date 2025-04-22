package org.pinggu.portforu.domain.oauth.user;

import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.oauth.provider.google.GoogleUserInfo;
import org.pinggu.portforu.domain.oauth.provider.kakao.KakaoUserInfo;
import org.pinggu.portforu.domain.oauth.provider.naver.NaverUserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OAuth2UserInfoFactory {

    public OAuth2UserInfo from(String provider, Map<String, Object> attributes) {
        return switch (provider.toLowerCase()) {
            case "google" -> new GoogleUserInfo(attributes);
            case "kakao" -> new KakaoUserInfo(attributes);
            case "naver" -> new NaverUserInfo(attributes);
            default -> throw new CustomException(HttpStatus.BAD_REQUEST, "지원하지 않는 로그인 서비스입니다.");
        };
    }

}
