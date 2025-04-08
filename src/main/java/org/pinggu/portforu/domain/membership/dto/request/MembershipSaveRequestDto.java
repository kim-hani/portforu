package org.pinggu.portforu.domain.membership.dto.request;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MembershipSaveRequestDto {

    @NotNull(message = "멤버쉽 이름은 필수입니다.")
    @Size(max = 50, message = "50글자 이내로 입력하세요.")
    private String name;

    @NotNull(message = "가격은 필수입니다.")
    @Min(value = 1, message = "가격은 0 이상이어야 합니다.")
    private Integer price;

    @NotNull(message = "수량은 필수입니다.")
    @Min(value = 1, message = "수량은 0이상이여야 합니다.")
    private Integer quantity;

    @NotNull(message = "사용년도는 필수입니다.")
    @Min(value = 2025, message = "사용년도는 현재년도보다 이상이여야 합니다.")
    private Integer year;

}
