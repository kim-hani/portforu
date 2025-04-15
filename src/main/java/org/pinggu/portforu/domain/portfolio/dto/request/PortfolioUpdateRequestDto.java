package org.pinggu.portforu.domain.portfolio.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class PortfolioUpdateRequestDto {

    @Size(max = 50, message = "제목은 50자까지 입력 가능합니다.")
    private String title;

    private String description;

    private MultipartFile imageFile;
}