package org.pinggu.portforu.common.dto;

import lombok.Getter;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class AuthMember {

    private final Long id;
    private final String email;
    private final String name;
    private final String phoneNumber;
    private final String address;
    private final Set<GrantedAuthority> authorities;
    private final String provider;

    // 권한이 한개인 경우
    public AuthMember(Long id, String email, String name, String phoneNumber, String address, UserRole userRole, String provider) {
        this(id, email, name, phoneNumber, address, Set.of(userRole), provider);
    }

    // 권한이 여러개인 경우
    public AuthMember(Long id, String email, String name, String phoneNumber, String address, Set<UserRole> userRoles, String provider) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.authorities = userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());
        this.provider = provider;
    }

    public UserRole getUserRole() {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .map(UserRole::valueOf)
                .findFirst()
                .orElseThrow(() -> new CustomException(HttpStatus.FORBIDDEN, "사용자 권한이 유효하지 않습니다."));
    }

    // 권한 여러개 반환
    public Set<UserRole> getUserRoles() {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .map(UserRole::valueOf)
                .collect(Collectors.toSet());
    }

}