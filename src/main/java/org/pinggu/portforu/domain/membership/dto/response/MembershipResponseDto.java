package org.pinggu.portforu.domain.membership.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pinggu.portforu.domain.membership.entity.Membership;

import java.time.LocalDateTime;

@Getter
@Builder
public class MembershipResponseDto {
    private final Long id;
    private final String name;
    private final Integer price;
    private final Integer quantity;
    private final Integer year;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;

    public static MembershipResponseDto from(Membership membership) {
        return MembershipResponseDto.builder()
                .id(membership.getId())
                .name(membership.getName())
                .price(membership.getPrice())
                .quantity(membership.getQuantity())
                .year(membership.getYear())
                .createdAt(membership.getCreatedAt())
                .updatedAt(membership.getUpdatedAt())
                .deletedAt(membership.getDeletedAt())
                .build();
    }
}
