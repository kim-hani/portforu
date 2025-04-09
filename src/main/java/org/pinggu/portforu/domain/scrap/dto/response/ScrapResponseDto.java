package org.pinggu.portforu.domain.scrap.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pinggu.portforu.domain.scrap.entity.Scrap;

import java.time.Instant;

@Getter
@Builder
public class ScrapResponseDto {

    private final Long id;
    private final Long memberId;
    private final Long jobPostingId;
    private final Instant createdAt;
    private final Instant deletedAt;

    public static ScrapResponseDto from(Scrap scrap) {
        return ScrapResponseDto.builder()
                .id(scrap.getId())
                .memberId(scrap.getMember().getId())
                .jobPostingId(scrap.getJobPosting().getId())
                .createdAt(scrap.getCreatedAt())
                .deletedAt(scrap.getDeletedAt())
                .build();
    }

}
