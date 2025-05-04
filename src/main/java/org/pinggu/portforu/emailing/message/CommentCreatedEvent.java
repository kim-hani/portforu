package org.pinggu.portforu.emailing.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreatedEvent {
    private Long postId;
    private Long commentId;
    private Long receiverMemberId;
    private String receiverEmail;
    private String portfolioTitle;
    private String commentContent;
}
