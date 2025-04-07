package org.pinggu.portforu.common.dto;

import lombok.Getter;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public class AuthMember {

    private final Long id;
    private final String email;
    private final String name;
    private final String phoneNumber;
    private final String address;
    private final Collection<? extends GrantedAuthority> authorities;

    public AuthMember(Long id, String email, String name, String phoneNumber, String address, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.authorities = List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    public UserRole getUserRole() {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .map(UserRole::valueOf)
                .findFirst()
                .orElseThrow(() -> new CustomException(HttpStatus.FORBIDDEN, "사용자 권한이 유효하지 않습니다."));
    }

}
