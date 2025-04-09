package org.pinggu.portforu.common.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column
    private Instant updatedAt;

    @Column
    private Instant deletedAt;

    public boolean isDeleted() {
        return deletedAt != null;
    }

    public Long delete() {
        this.deletedAt = Instant.now();
        return this.id;
    }

    public void restore() {
        this.deletedAt = null;
    }

}

