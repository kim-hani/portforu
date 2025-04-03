package org.pinggu.portforu.common.dto;

import lombok.Getter;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public class AuthMember {

    private final Long Id;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;

    public AuthMember(Long Id, String email, UserRole userRole) {
        this.Id = Id;
        this.email = email;
        this.authorities = List.of(new SimpleGrantedAuthority(userRole.name()));
    }

}
