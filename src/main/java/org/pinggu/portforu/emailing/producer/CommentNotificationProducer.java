package org.pinggu.portforu.emailing.producer;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.emailing.message.CommentCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentNotificationProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendCommentCreatedEvent(CommentCreatedEvent event) {
        rabbitTemplate.convertAndSend("comment-created-queue", event);
    }
}
