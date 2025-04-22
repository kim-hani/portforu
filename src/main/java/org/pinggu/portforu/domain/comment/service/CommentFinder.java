package org.pinggu.portforu.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.comment.entity.Comment;
import org.pinggu.portforu.domain.comment.repository.CommentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentFinder {

    private final CommentRepository commentRepository;

    public Comment findCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));

        if (comment.getIsDeleted()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 삭제된 댓글입니다.");
        }

        return comment;
    }

}
