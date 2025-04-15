package org.pinggu.portforu.common.domain.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    /**
     * S3에 이미지 업로드
     */
    public String uploadImage(MultipartFile image) throws IOException {
        String fileName ="active/" + UUID.randomUUID() + "_" + image.getOriginalFilename();
        return uploadFile(image, fileName);
    }

    /**
     * 실제 업로드 처리
     */
    private String uploadFile(MultipartFile file, String fileName) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata);

        amazonS3.putObject(putObjectRequest);

        return getPublicUrl(fileName);
    }

    /**
     * 기존 파일을 inactive/ 경로로 복사 후 삭제 → 라이프사이클 정책에 의해 자동 삭제 유도
     */
    public void markFileAsInactive(String fileUrl) {
        if (fileUrl == null || fileUrl.isBlank()) return;

        String originalFileName = extractFileName(fileUrl);
        String fileNameOnly = originalFileName.substring(originalFileName.lastIndexOf("/") + 1);

        String inactiveFileName = "inactive/" + fileNameOnly;

        // S3 객체 복사
        CopyObjectRequest copyObjRequest = new CopyObjectRequest(bucket, originalFileName, bucket, inactiveFileName);
        amazonS3.copyObject(copyObjRequest);

        // 기존 active 경로 삭제
        amazonS3.deleteObject(bucket, originalFileName);
    }

    /**
     * 파일 URL에서 S3 키 추출
     */
    private String extractFileName(String fileUrl) {
        String[] parts = fileUrl.split(".amazonaws.com/");
        return parts.length > 1 ? parts[1] : "";
    }

    /**
     * 파일의 Public URL 생성
     */
    private String getPublicUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName(), fileName);
    }
}
