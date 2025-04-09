package org.pinggu.portforu.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    @NotBlank(message = "내용을 작성해주세요")
    private String content;
}
