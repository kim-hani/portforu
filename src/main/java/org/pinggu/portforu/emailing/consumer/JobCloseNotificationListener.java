package org.pinggu.portforu.emailing.consumer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.emailing.message.CommentCreatedEvent;
import org.pinggu.portforu.emailing.service.CommentNotificationService;
import org.pinggu.portforu.emailing.service.JobCloseNotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobCloseNotificationListener {

    private final JobCloseNotificationService jobCloseNotificationService;

    @RabbitListener(queues = "job.closing-soon.queue")
    public void handleJobClosingSoon(@Payload JobClosingSoonMessage message) {
        log.info("받은 메시지: jobPostingId={}, jobTitle={}", message.getJobPostingId(), message.getJobTitle());
        jobCloseNotificationService.notifyClosingSoon(message.getJobPostingId(), message.getJobTitle());
    }

    @Getter
    @NoArgsConstructor
    public static class JobClosingSoonMessage {
        private Long jobPostingId;
        private String jobTitle;
    }

}
