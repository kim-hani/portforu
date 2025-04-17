package org.pinggu.portforu.domain.payment.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.domain.payment.entity.Payment;
import org.pinggu.portforu.domain.payment.enums.PaymentStatus;
import org.pinggu.portforu.domain.payment.repository.PaymentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentScheduler {

    private final PaymentRepository paymentRepository;

    @Scheduled(cron = "0 * * * * *")
    public void expireUnpaidPayments() {
        Instant tenMinutesAgo = Instant.now().minus(Duration.ofMinutes(10));

        List<Payment> targets = paymentRepository
                .findByStatusAndCreatedAtBefore(PaymentStatus.PENDING, tenMinutesAgo);

        for (Payment payment : targets) {
            payment.expire();
            paymentRepository.save(payment);
            log.info("[만료 처리] paymentId = {}, createdAt = {}", payment.getId(), payment.getCreatedAt());
        }
    }
}