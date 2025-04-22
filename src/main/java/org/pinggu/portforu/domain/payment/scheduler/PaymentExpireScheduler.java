package org.pinggu.portforu.domain.payment.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.domain.payment.entity.Payment;
import org.pinggu.portforu.domain.payment.enums.PaymentStatus;
import org.pinggu.portforu.domain.payment.repository.PaymentRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentExpireScheduler {
    // redis 쓰기전까지 우선 jdbc는 사용 안하고 이코드로 구독요청시 20분뒤에 딱한번 식행 상태가 아직 panding이면 expired처리를 subscribeservice로직에 넣음
    private final PaymentRepository paymentRepository;

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

    public void scheduleExpire(Long subscribeId, Duration delay) {
        log.info(" 구독 ID {} 에 대해 {}분 후 결제 만료 예약", subscribeId, delay.toMinutes());

        executorService.schedule(() -> {
            expireIfPending(subscribeId);
        }, delay.toMinutes(), TimeUnit.MINUTES);
    }

    @Transactional
    protected void expireIfPending(Long subscribeId) {
        Optional<Payment> optionalPayment = paymentRepository.findBySubscribeId(subscribeId);
        optionalPayment.ifPresent(payment -> {
            if (payment.getStatus() == PaymentStatus.PENDING && payment.getPaymentKey() == null) {
                payment.expire();
                paymentRepository.save(payment);
                log.info(" 결제 만료 처리 완료: subscribeId={}", subscribeId);
            }
        });
    }
}