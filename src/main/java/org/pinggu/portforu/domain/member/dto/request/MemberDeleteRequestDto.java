package org.pinggu.portforu.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberDeleteRequestDto {

    @NotBlank(message = "현재 비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "비밀번호를 확인하세요.")
    private String passwordConfirm;

}