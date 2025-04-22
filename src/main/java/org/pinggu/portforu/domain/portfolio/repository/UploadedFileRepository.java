package org.pinggu.portforu.domain.portfolio.repository;

import org.pinggu.portforu.domain.portfolio.entity.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {

    Optional<UploadedFile> findByFileUrl(String fileUrl);

    @Query("SELECT f FROM UploadedFile f WHERE f.used = false AND f.createdAt < :cutoff")
    List<UploadedFile> findUnusedFilesOlderThan(@Param("cutoff") Instant cutoff);

}
