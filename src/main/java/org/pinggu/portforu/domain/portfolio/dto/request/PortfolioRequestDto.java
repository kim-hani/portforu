package org.pinggu.portforu.domain.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PortfolioRequestDto {


    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 50, message = "제목은 50자까지 입력 가능합니다.")
    private String title;

    @NotBlank(message = "게시물의 내용을 작성해주세요.")
    private String description;


    @Size(max = 500, message = "파일url은 500자까지 입력 가능합니다.")
    private String fileUrl;
}
