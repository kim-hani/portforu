package org.pinggu.portforu.domain.scrap.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pinggu.portforu.domain.scrap.entity.Scrap;

import java.time.Instant;

@Getter
@Builder
public class ScrapDetailResponseDto {

    private final Long id;
    private final Long memberId;
    private final String name;
    private final String industry;
    private final String address;
    private final String salary;
    private final String postingDate;
    private final String closingDate;
    private final Instant createdAt;
    private final Instant deletedAt;

    public static ScrapDetailResponseDto from(Scrap scrap) {
        return ScrapDetailResponseDto.builder()
                .id(scrap.getId())
                .memberId(scrap.getMember().getId())
                .name(scrap.getJobPosting().getName())
                .industry(scrap.getJobPosting().getIndustry())
                .address(scrap.getJobPosting().getAddress())
                .salary(scrap.getJobPosting().getSalary())
                .postingDate(scrap.getJobPosting().getPostingDate())
                .closingDate(scrap.getJobPosting().getClosingDate())
                .createdAt(scrap.getCreatedAt())
                .deletedAt(scrap.getDeletedAt())
                .build();
    }

}
