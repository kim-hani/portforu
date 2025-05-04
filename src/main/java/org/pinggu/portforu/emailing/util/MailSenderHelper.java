package org.pinggu.portforu.emailing.util;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.domain.member.entity.Member;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;


@Slf4j
@Component
@RequiredArgsConstructor
public class MailSenderHelper {

    private final JavaMailSender mailSender;
    private final SlackNotifier slackNotifier;

    // HTML 메일용
    public void sendIfSubscribed(Member member, BiConsumer<MimeMessageHelper, Member> messageBuilder) {
        if (!member.getIsEmailSubscribed()) {
            log.info("이메일 수신 거부한 회원입니다. 발송 스킵: {}", member.getEmail());
            return;
        }

        String actualEmail = resolveActualEmail(member);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setTo(actualEmail);

            // 외부에서 제목/본문 구성
            messageBuilder.accept(helper, member);

            mailSender.send(mimeMessage);
            log.info("이메일 발송 완료 (HTML): to={}, subject={}", member.getEmail(), helper.getMimeMessage().getSubject());

        } catch (Exception e) {
            log.error("이메일 발송 실패: to={}, error={}", member.getEmail(), e.getMessage(), e);
            slackNotifier.send("[메일 발송 실패]\n수신자: " + member.getEmail() + "\n오류: " + e.getMessage());
        }
    }

    // 소셜 로그인 유저는 언더바 기준으로 뒷부분만 추출
    private String resolveActualEmail(Member member) {
        String email = member.getEmail();

        if (member.getProvider() == null) {
            return email;
        }

        int underscore = email.indexOf("_");
        if (underscore != -1 && underscore + 1 < email.length()) {
            return email.substring(underscore + 1);
        }

        return email; // fallback
    }

    // Unsubscribe 링크 HTML 포맷
    public String appendUnsubscribeLink(String originalHtml, Member member) {
        String token = member.getUnsubscribeToken();
        String unsubscribeUrl = "https://portforu.online/emails/unsubscribe?token=" + token;
        return originalHtml + String.format(
                """
                <br><br>
                <p style="font-size:12px; color:gray;">
                    메일 수신을 원치 않으시면 아래 버튼을 눌러주세요.<br>
                    <a href="%s" style="color:#1a73e8; text-decoration:underline;">구독 해지하기</a>
                </p>
                """,
                unsubscribeUrl
        );
    }
}
