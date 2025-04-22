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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @Member
    @PostMapping("/{portfolioId}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> saveComment(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable("portfolioId") Long portfolioId,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {
        return ResponseEntity.ok().body(ApiResponse.of(commentService.saveComment(authMember, portfolioId, requestDto)));
    }

    @Member
    @GetMapping("/{portfolioId}")
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> findAllComments(
            @PathVariable("portfolioId") Long portfolioId) {
        return ResponseEntity.ok(ApiResponse.of(commentService.findAllComments(portfolioId)));
    }

    @Member
    @PutMapping("/{portfolioId}/{commentId}")
    public ResponseEntity<ApiResponse<String>> updateComment(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable("portfolioId") Long portfolioId,
            @PathVariable("commentId") Long commentId,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {
        commentService.updateComment(authMember, portfolioId, commentId, requestDto);
        return ResponseEntity
                .ok(ApiResponse.of("댓글 수정이 완료되었습니다."));
    }

    @Member
    @DeleteMapping("/{portfolioId}/{commentId}")
    public ResponseEntity<ApiResponse<Long>> deleteComment(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable("portfolioId") Long portfolioId,
            @PathVariable("commentId") Long commentId
    ) {
        return ResponseEntity.ok(ApiResponse.of(commentService.deleteComment(authMember, portfolioId, commentId)));
    }

}
