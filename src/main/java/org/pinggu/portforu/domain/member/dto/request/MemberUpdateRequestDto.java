package org.pinggu.portforu.domain.member.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MemberUpdateRequestDto {

    private String oldPassword;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
            message = "비밀번호는 영문자, 숫자, 특수문자를 최소 1글자씩 포함하며, 8자 이상 20자 이하이어야 합니다."
    )
    private String newPassword;

    @Size(max = 30, message = "30글자 이내로 입력하세요.")
    private String name;

    @Size(max = 30, message = "30글자 이내로 입력하세요.")
    private String phoneNumber;

    @Size(max = 255, message = "255글자 이내로 입력하세요.")
    private String address;

    public MemberUpdateRequestDto(String oldPassword, String newPassword, String name, String phoneNumber, String address) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
  
}