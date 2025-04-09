package org.pinggu.portforu.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.comment.dto.request.CommentRequestDto;
import org.pinggu.portforu.domain.comment.dto.response.CommentResponseDto;
import org.pinggu.portforu.domain.comment.entity.Comment;
import org.pinggu.portforu.domain.comment.repository.CommentRepository;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.member.repository.MemberRepository;
import org.pinggu.portforu.domain.portfolio.entity.Portfolio;
import org.pinggu.portforu.domain.portfolio.repository.PortfolioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PortfolioRepository portfolioRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CommentResponseDto saveComment(Long portfolioId, Long memberId, CommentRequestDto requestDto) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."));

        if (portfolio.isDeleted()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "삭제된 게시물입니다.");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .member(member)
                .portfolio(portfolio)
                .content(requestDto.getContent())
                .build();

        Comment savedComment = commentRepository.save(comment);
        return CommentResponseDto.from(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAllComments(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."));

        if (portfolio.isDeleted()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "삭제된 게시물입니다.");
        }

        List<Comment> comments = commentRepository.findByPortfolioId(portfolioId);

        return comments.stream()
                .filter(comment -> !comment.isDeleted())
                .map(CommentResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto updateComment(Long portfolioId, Long commentId, Long memberId, CommentRequestDto requestDto) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."));

        if (portfolio.isDeleted()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "삭제된 게시물입니다.");
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));

        if (comment.isDeleted()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "삭제된 댓글입니다.");
        }

        if (!comment.getMember().getId().equals(memberId)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "수정 권한이 없습니다.");
        }

        comment.update(requestDto.getContent());
        return CommentResponseDto.from(comment);
    }

    @Transactional
    public Long deleteComment(Long portfolioId, Long commentId, Long memberId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."));

        if (portfolio.isDeleted()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "삭제된 게시물입니다.");
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));

        if (comment.isDeleted()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "이미 삭제된 댓글입니다.");
        }

        if (!comment.getMember().getId().equals(memberId)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "삭제 권한이 없습니다.");
        }

        // 소프트 삭제 처리
        return comment.delete();
    }
}

