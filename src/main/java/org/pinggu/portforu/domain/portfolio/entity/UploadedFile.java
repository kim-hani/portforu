package org.pinggu.portforu.domain.portfolio.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "uploaded_files",
        indexes = {
                @Index(name = "idx_uploaded_file_url", columnList = "fileUrl", unique = true)
        }
)
public class UploadedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileUrl;

    private boolean used;

    private Instant createdAt;

    public UploadedFile(String fileUrl) {
        this.fileUrl = fileUrl;
        this.used = false;
        this.createdAt = Instant.now();
    }

    public void markUsed() {
        this.used = true;
    }

}
