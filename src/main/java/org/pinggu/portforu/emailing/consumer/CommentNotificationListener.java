package org.pinggu.portforu.emailing.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.emailing.message.CommentCreatedEvent;
import org.pinggu.portforu.emailing.service.CommentNotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentNotificationListener {

    private final CommentNotificationService commentNotificationService;

    @RabbitListener(queues = "comment-created-queue")
    public void handleCommentCreated(@Payload CommentCreatedEvent event) {
        log.info("댓글 알림 수신: postId={}, commentId={}, receiverMemberId={}",
                event.getPostId(), event.getCommentId(), event.getReceiverMemberId());

        commentNotificationService.notifyCommentCreated(event);
    }
}
