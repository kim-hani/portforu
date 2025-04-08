package org.pinggu.portforu.config;

import org.pinggu.portforu.common.dto.AuthMember;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final AuthMember authMember;

    public JwtAuthenticationToken(AuthMember authMember) {
        super(authMember.getAuthorities());
        this.authMember = authMember;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return authMember;
    }

}