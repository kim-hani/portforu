package org.pinggu.portforu.domain.membership.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pinggu.portforu.domain.membership.annotation.YearMinNow;

@Getter
@AllArgsConstructor
public class MembershipUpdateRequestDto {

    @Size(max = 50, message = "50글자 이내로 입력하세요.")
    private String name;

    @Min(value = 1, message = "가격은 0 이상이어야 합니다.")
    private Integer price;

    @Min(value = 1, message = "수량은 0이상이여야 합니다.")
    private Integer quantity;

    @YearMinNow
    private Integer year;

}
