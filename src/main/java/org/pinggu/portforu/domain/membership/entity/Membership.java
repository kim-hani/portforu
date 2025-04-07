package org.pinggu.portforu.domain.membership.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pinggu.portforu.common.domain.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "memberships")
public class Membership extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membership_id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Integer year;

    @Builder
    public Membership(String name, Integer price, Integer quantity, Integer year) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.year = year;
    }

    public void update(String name, Integer price, Integer quantity, Integer year) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.year = year;
    }
}
