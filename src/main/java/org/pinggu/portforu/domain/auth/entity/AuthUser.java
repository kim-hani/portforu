package org.pinggu.portforu.domain.auth.entity;

import lombok.Getter;
import org.pinggu.portforu.domain.user.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public class AuthUser {

    private final Long Id;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;

    public AuthUser(Long Id, String email, UserRole userRole) {
        this.Id = Id;
        this.email = email;
        this.authorities = List.of(new SimpleGrantedAuthority(userRole.name()));
    }

}
