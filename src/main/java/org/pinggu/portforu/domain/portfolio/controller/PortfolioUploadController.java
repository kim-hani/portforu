package org.pinggu.portforu.domain.portfolio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.portfolio.entity.UploadedFile;
import org.pinggu.portforu.domain.portfolio.repository.UploadedFileRepository;
import org.pinggu.portforu.domain.portfolio.service.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "포트폴리오 파일 업로드 API", description = "포트폴리오 파일을 S3에 업로드합니다.")
@RestController
@RequestMapping("/api/v1/portfolios/file")
@RequiredArgsConstructor
public class PortfolioUploadController {

    private final S3Service s3Service;
    private final UploadedFileRepository uploadedFileRepository;

    @Operation(summary = "파일 업로드",
            description = "MultipartFile로 받은 포트폴리오 파일을 S3에 업로드합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> uploadPortfolioFile(
            @Parameter(
                    description = "업로드할 포트폴리오 파일",
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(type = "string", format = "binary")
                    )
            )
            @RequestPart("portfolioFile") MultipartFile portfolioFile
    ) {
        try {
            String url = s3Service.uploadPortfolioFile(portfolioFile);
            uploadedFileRepository.save(new UploadedFile(url));
            return ResponseEntity.ok(ApiResponse.of(url));
        } catch (IOException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "파일 업로드 실패");
        }
    }
}
