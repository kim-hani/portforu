package org.pinggu.portforu.domain.comment.controller;

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


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/portfolios/{portfolioId}/comments")
public class CommentController {

    private final CommentService commentService;

    @Member
    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseDto>> saveComment(
            @PathVariable("portfolioId") Long portfolioId,
            @AuthenticationPrincipal AuthMember authMember,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(
                commentService.saveComment(portfolioId, authMember.getId(), requestDto)));
    }

    @Member
    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> updateComment(
            @PathVariable("portfolioId") Long portfolioId,
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal AuthMember authMember,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(
                commentService.updateComment(portfolioId, commentId, authMember.getId(), requestDto)));
    }

    @Member
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<String>> deleteComment(
            @PathVariable("portfolioId") Long portfolioId,
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        commentService.deleteComment(portfolioId, commentId, authMember.getId());
        return ResponseEntity.ok(ApiResponse.of("댓글이 삭제되었습니다."));
    }
}
