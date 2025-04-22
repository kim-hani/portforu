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
    private final Long jobPostingId;
    private final String name;
    private final String industry;
    private final String address;
    private final String salary;
    private final Instant postingDate;
    private final Instant closingDate;
    private final Instant createdAt;
    private final Boolean isDeleted;

    public static ScrapDetailResponseDto from(Scrap scrap) {
        return ScrapDetailResponseDto.builder()
                .id(scrap.getId())
                .memberId(scrap.getMember().getId())
                .jobPostingId(scrap.getJobPosting().getId())
                .name(scrap.getJobPosting().getName())
                .industry(scrap.getJobPosting().getIndustry())
                .address(scrap.getJobPosting().getAddress())
                .salary(scrap.getJobPosting().getSalary())
                .postingDate(scrap.getJobPosting().getPostingDate())
                .closingDate(scrap.getJobPosting().getClosingDate())
                .createdAt(scrap.getCreatedAt())
                .isDeleted(scrap.getIsDeleted())
                .build();
    }

}
