package org.pinggu.portforu.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.annotation.Member;
import org.pinggu.portforu.common.dto.ApiResponse;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.domain.comment.dto.request.CommentRequestDto;
import org.pinggu.portforu.domain.comment.dto.response.CommentResponseDto;
import org.pinggu.portforu.domain.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(name = "댓글 API", description = "댓글 생성·조회·수정·삭제")
@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성", description = "지정된 포트폴리오에 댓글을 작성합니다.")
    @Member
    @PostMapping("/{portfolioId}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> saveComment(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable Long portfolioId,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                commentService.saveComment(authMember, portfolioId, requestDto)
        ));
    }

    @Operation(summary = "댓글 목록 조회", description = "지정된 포트폴리오의 모든 댓글을 조회합니다.")
    @Member
    @GetMapping("/{portfolioId}")
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> findAllComments(
            @PathVariable Long portfolioId
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                commentService.findAllComments(portfolioId)
        ));
    }

    @Operation(summary = "댓글 수정", description = "회원 본인이 작성한 댓글을 수정합니다.")
    @Member
    @PutMapping("/{portfolioId}/{commentId}")
    public ResponseEntity<ApiResponse<String>> updateComment(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable Long portfolioId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {
        commentService.updateComment(authMember, portfolioId, commentId, requestDto);
        return ResponseEntity.ok(ApiResponse.of("댓글 수정이 완료되었습니다."));
    }

    @Operation(summary = "댓글 삭제", description = "회원 본인이 작성한 댓글을 삭제합니다.")
    @Member
    @DeleteMapping("/{portfolioId}/{commentId}")
    public ResponseEntity<ApiResponse<Long>> deleteComment(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable Long portfolioId,
            @PathVariable Long commentId
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                commentService.deleteComment(authMember, portfolioId, commentId)
        ));
    }
}
