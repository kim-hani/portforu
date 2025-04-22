package org.pinggu.portforu.domain.portfolio.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadPortfolioFile(MultipartFile portfolioFile) throws IOException {
        String fileName = "active/" + UUID.randomUUID() + "_" + portfolioFile.getOriginalFilename();
        return uploadFile(portfolioFile, fileName);
    }

    private String uploadFile(MultipartFile file, String fileName) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        amazonS3.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata));
        return getPublicUrl(fileName);
    }

    public void markFileAsInactive(String fileUrl) {
        if (fileUrl == null || fileUrl.isBlank()) return;

        String originalFileName = extractFileName(fileUrl);
        String fileNameOnly = originalFileName.substring(originalFileName.lastIndexOf("/") + 1);
        String inactiveFileName = "inactive/" + fileNameOnly;

        amazonS3.copyObject(new CopyObjectRequest(bucket, originalFileName, bucket, inactiveFileName));
        amazonS3.deleteObject(bucket, originalFileName);
    }

    private String extractFileName(String fileUrl) {
        String[] parts = fileUrl.split(".amazonaws.com/");
        return parts.length > 1 ? parts[1] : "";
    }

    private String getPublicUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName(), fileName);
    }

}
