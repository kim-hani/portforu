package org.pinggu.portforu.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequestDto {

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식이 아닙니다.")
    @Size(max = 100, message = "100글자 이내로 입력하세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
            message = "비밀번호는 영문자, 숫자, 특수문자를 최소 1글자씩 포함하며, 8자 이상 20자 이하이어야 합니다."
    )
    private String password;

    @NotBlank(message = "이름을 입력하세요.")
    @Size(max = 30, message = "30글자 이내로 입력하세요.")
    private String name;

    @NotBlank(message = "전화번호를 입력하세요.")
    @Size(max = 30, message = "30글자 이내로 입력하세요.")
    private String phoneNumber;

    @NotBlank(message = "주소를 입력하세요.")
    @Size(max = 255, message = "255글자 이내로 입력하세요.")
    private String address;

}
