package org.pinggu.portforu.domain.portfolio.scheduler;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.domain.portfolio.entity.UploadedFile;
import org.pinggu.portforu.domain.portfolio.repository.UploadedFileRepository;
import org.pinggu.portforu.domain.portfolio.service.S3Service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadCleanupScheduler {

    private final UploadedFileRepository uploadedFileRepository;
    private final S3Service s3Service;

    @Scheduled(cron = "0 0 * * * *") //
    public void cleanupUnusedFiles() {
        Instant cutoff = Instant.now().minus(Duration.ofHours(1));
        List<UploadedFile> unusedFiles = uploadedFileRepository.findUnusedFilesOlderThan(cutoff);

        for (UploadedFile file : unusedFiles) {
            s3Service.markFileAsInactive(file.getFileUrl());
            uploadedFileRepository.delete(file);
        }
    }

}