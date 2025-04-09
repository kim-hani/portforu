package org.pinggu.portforu.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pinggu.portforu.common.domain.BaseEntity;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.domain.member.enums.UserRole;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Column(length = 60, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Builder
    public Member(String email, String password, String name, String phoneNumber, String address, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.userRole = userRole;
    }

    private Member(Long id, String email, String name, String phoneNumber, String address, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.userRole = userRole;
    }

    public void updateInfo(String name, String phoneNumber, String address) {
        if (name != null) this.name = name;
        if (phoneNumber != null) this.phoneNumber = phoneNumber;
        if (address != null) this.address = address;
    }

    public void updatePassword(String newPassword) {
        if (newPassword != null) this.password = newPassword;
    }

    public static Member fromAuthMember(AuthMember authMember) {
        return new Member(authMember.getId(), authMember.getEmail(), authMember.getName(), authMember.getPhoneNumber(), authMember.getAddress(), authMember.getUserRole());
    }

}