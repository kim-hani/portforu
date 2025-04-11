package org.pinggu.portforu.domain.oauth.provider.google;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.domain.oauth.user.OAuth2UserInfo;

import java.util.Map;

@RequiredArgsConstructor
public class GoogleUserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getPhone() {
        return "";
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

}
