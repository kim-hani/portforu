package org.pinggu.portforu.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.member.enums.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoleAccessAspect {

    @Before("@annotation(org.pinggu.portforu.common.annotation.Admin)")
    public void checkAdminAccess(JoinPoint joinPoint) {
        checkRole(UserRole.ROLE_ADMIN, "관리자 권한이 필요합니다.");
    }

    @Before("@annotation(org.pinggu.portforu.common.annotation.Member)")
    public void checkMemberAccess(JoinPoint joinPoint) {
        checkRole(UserRole.ROLE_USER, "일반 사용자 권한이 필요합니다.");
    }

    private void checkRole(UserRole requiredRole, String errorMessage) {
        AuthMember authMember = (AuthMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserRole role = authMember.getUserRole();

        if (role != requiredRole) {
            throw new CustomException(HttpStatus.FORBIDDEN, errorMessage);
        }
    }

}
