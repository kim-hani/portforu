package org.pinggu.portforu.domain.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.common.dto.AuthMember;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.comment.dto.request.CommentRequestDto;
import org.pinggu.portforu.domain.comment.dto.response.CommentResponseDto;
import org.pinggu.portforu.domain.comment.entity.Comment;
import org.pinggu.portforu.domain.comment.repository.CommentRepository;
import org.pinggu.portforu.domain.jobposting.entity.JobPosting;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.portfolio.entity.Portfolio;
import org.pinggu.portforu.domain.portfolio.service.PortfolioFinder;
import org.pinggu.portforu.domain.subscribe.validator.SubscribeValidator;
import org.pinggu.portforu.emailing.message.CommentCreatedEvent;
import org.pinggu.portforu.emailing.producer.CommentNotificationProducer;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final PortfolioFinder portfolioFinder;
    private final CommentFinder commentFinder;
    private final SubscribeValidator subscribeValidator;
    private final CommentRepository commentRepository;
    private final CommentNotificationProducer commentNotificationProducer;

    @Transactional
    public CommentResponseDto saveComment(AuthMember authMember, Long portfolioId, CommentRequestDto requestDto) {
        Portfolio portfolio = portfolioFinder.findPortfolioById(portfolioId);

        Member member = Member.fromAuthMember(authMember);

        if (!portfolio.getMember().getId().equals(member.getId())) {
            if (!subscribeValidator.isSubscribed(member.getId())) {
                throw new CustomException(HttpStatus.UNAUTHORIZED, "댓글을 작성할 권한이 없습니다.");
            }
        }

        Comment comment = Comment.builder()
                .member(member)
                .portfolio(portfolio)
                .content(requestDto.getContent())
                .build();

        Comment savedComment = commentRepository.save(comment);

        // 알림 발행 로직 추가
        if (!portfolio.getMember().getId().equals(member.getId())) {
            CommentCreatedEvent event = new CommentCreatedEvent(
                    portfolio.getId(),
                    savedComment.getId(),
                    portfolio.getMember().getId(),
                    portfolio.getMember().getEmail(),
                    portfolio.getTitle(),
                    requestDto.getContent()
            );
            commentNotificationProducer.sendCommentCreatedEvent(event);
            log.info("[댓글 알림] 큐 발행 완료: {}", event);
        }


        return CommentResponseDto.from(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAllComments(Long portfolioId) {
        portfolioFinder.findPortfolioById(portfolioId);

        List<Comment> comments = commentRepository.findByPortfolioId(portfolioId, Sort.by(Sort.Order.desc("id")));

        return comments.stream()
                .filter(comment -> !comment.getIsDeleted())
                .map(CommentResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateComment(AuthMember authMember, Long portfolioId, Long commentId, CommentRequestDto requestDto) {
        portfolioFinder.findPortfolioById(portfolioId);

        Comment comment = commentFinder.findCommentById(commentId);

        if (!comment.getMember().getId().equals(authMember.getId())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "수정 권한이 없습니다.");
        }
        comment.update(requestDto.getContent());
    }

    @Transactional
    public Long deleteComment(AuthMember authMember, Long portfolioId, Long commentId) {
        portfolioFinder.findPortfolioById(portfolioId);

        Comment comment = commentFinder.findCommentById(commentId);

        if (!comment.getMember().getId().equals(authMember.getId())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
        return comment.getId();
    }

}


