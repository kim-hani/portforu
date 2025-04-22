package org.pinggu.portforu.domain.membership.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pinggu.portforu.domain.membership.entity.Membership;

import java.time.Instant;

@Getter
@Builder
public class MembershipResponseDto {
    private final Long id;
    private final String name;
    private final Integer price;
    private final Integer quantity;
    private final Integer year;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final Boolean isDeleted;

    public static MembershipResponseDto from(Membership membership) {
        return MembershipResponseDto.builder()
                .id(membership.getId())
                .name(membership.getName())
                .price(membership.getPrice())
                .quantity(membership.getQuantity())
                .year(membership.getYear())
                .createdAt(membership.getCreatedAt())
                .updatedAt(membership.getUpdatedAt())
                .isDeleted(membership.getIsDeleted())
                .build();
    }

}
