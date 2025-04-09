package org.pinggu.portforu.domain.portfolio.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PortfolioUpdateRequestDto {

    @Size(max = 50, message = "제목은 50자까지 입력 가능합니다.")
    private String title;

    private String description;

    @Size(max = 500, message = "파일url은 500자까지 입력 가능합니다.")
    private String fileUrl;

}