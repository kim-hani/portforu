package org.pinggu.portforu.domain.oauth.user;

import java.util.Map;

public interface OAuth2UserInfo {

    String getProvider();

    String getEmail();

    String getName();

    String getPhone();

    Map<String, Object> getAttributes();

}
