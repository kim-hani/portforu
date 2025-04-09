package org.pinggu.portforu.domain.member.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberUpdateRequestDto {

    @Size(max = 30, message = "30글자 이내로 입력하세요.")
    private String name;

    @Size(max = 30, message = "30글자 이내로 입력하세요.")
    private String phoneNumber;

    @Size(max = 255, message = "255글자 이내로 입력하세요.")
    private String address;

}