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
public class AdminAccessAspect {

    @Before("@annotation(org.pinggu.portforu.common.annotation.Admin)")
    public void adminApiAccess(JoinPoint joinPoint) {
        AuthMember authMember = (AuthMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserRole role = authMember.getUserRole();

        if (role != UserRole.ROLE_ADMIN) {
            throw new CustomException(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다.");
        }
    }

}
