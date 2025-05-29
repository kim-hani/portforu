package org.pinggu.portforu.domain.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.pinggu.portforu.domain.auth.verify.PhoneVerification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class SmsService {

    private final DefaultMessageService messageService;
    private final PhoneVerification phoneVerification;

    @Value("${coolsms.sender}")
    private String from;

    public void sendVerificationCode(String phoneNumber) {
        String code = generateCode();
        phoneVerification.saveCode(phoneNumber, code);
        sendSms(phoneNumber, "[포트포유] 인증번호는 [" + code + "]입니다.");
    }

    public boolean verifyCode(String phoneNumber, String inputCode) {
        String stored = phoneVerification.getCode(phoneNumber);
        if (stored != null && stored.equals(inputCode)) {
            phoneVerification.markVerified(phoneNumber);
            phoneVerification.deleteCode(phoneNumber);
            return true;
        }
        return false;
    }

    private void sendSms(String phoneNumber, String text) {
        Message message = new Message();
        message.setFrom(from);
        message.setTo(phoneNumber);
        message.setText(text);
        messageService.sendOne(new SingleMessageSendingRequest(message));
    }

    private String generateCode() {
        return String.valueOf((int) (Math.random() * 900000) + 100000); // 6자리
    }
}
