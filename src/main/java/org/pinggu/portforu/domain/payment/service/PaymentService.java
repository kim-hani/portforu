package org.pinggu.portforu.domain.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.domain.membership.entity.Membership;
import org.pinggu.portforu.domain.payment.entity.Payment;
import org.pinggu.portforu.domain.payment.enums.PaymentStatus;
import org.pinggu.portforu.domain.payment.exception.PaymentFailedException;
import org.pinggu.portforu.domain.payment.repository.PaymentRepository;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.pinggu.portforu.domain.subscribe.repository.SubscribeRepository;
import org.pinggu.portforu.domain.subscribe.service.SubscribeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final RestTemplate restTemplate;
    private final PaymentRepository paymentRepository;
    private final SubscribeService subscribeService;
    private final SubscribeRepository subscribeRepository;

    @Value("${toss.test-secret-key}")
    private String secretKey;

    public void handleSuccessPayment(String paymentKey, String orderId, Long amount) {
        Long subscribeId = parseSubscribeId(orderId);
        try {
            Payment payment = paymentRepository.findBySubscribeId(subscribeId)
                    .orElseThrow(() -> new PaymentFailedException("결제 정보가 없습니다."));

            if (payment.getStatus() == PaymentStatus.COMPLETED) {
                log.info("중복 결제 요청 차단됨: orderId={}, subscribeId={}", orderId, subscribeId);
                throw new PaymentFailedException("이미 결제가 완료된 주문입니다.");
            }

            Subscribe subscribe = payment.getSubscribe();
            Membership membership = subscribe.getMembership();

            long currentCount = subscribeRepository.countActiveByMembership(membership, Instant.now());
            if (currentCount >= membership.getQuantity()) {
                try {
                    cancelTossPayment(paymentKey, "멤버십 정원 초과로 결제 취소됨");
                } catch (Exception e) {
                    log.warn(" Toss 결제 취소 실패: {}", e.getMessage());
                }

                payment.fail();
                paymentRepository.save(payment);

                subscribe.fail();
                subscribeRepository.save(subscribe);

                log.warn("결제 실패 - 정원 초과: orderId={}, subscribeId={}", orderId, subscribeId);
                throw new PaymentFailedException("멤버십 정원이 초과되어 결제가 취소되었습니다.");
            }

            // Toss 결제 승인 요청
            String url = "https://api.tosspayments.com/v1/payments/confirm";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Basic " + Base64.getEncoder()
                    .encodeToString((secretKey + ":").getBytes()));

            Map<String, Object> body = new HashMap<>();
            body.put("paymentKey", paymentKey);
            body.put("orderId", orderId);
            body.put("amount", amount);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            restTemplate.postForEntity(url, request, String.class);

            payment.assignPaymentKey(paymentKey);
            payment.complete();
            paymentRepository.save(payment);

            subscribe.activate();
            subscribeRepository.save(subscribe);

            subscribeService.updateSubscriptionStatus(subscribeId, PaymentStatus.COMPLETED);

            log.info("결제 완료: orderId={}, subscribeId={}, amount={}", orderId, subscribeId, amount);

        } catch (HttpClientErrorException e) {
            Subscribe subscribe = subscribeRepository.findById(subscribeId)
                    .orElseThrow(() -> new PaymentFailedException("구독 정보를 찾을 수 없습니다."));
            subscribe.fail();
            subscribeRepository.save(subscribe);

            log.warn("Toss 결제 승인 실패: orderId={}, subscribeId={}, error={}", orderId, subscribeId, e.getMessage());

            throw new PaymentFailedException("Toss 결제 승인 실패: " + e.getMessage());
        }
    }

    public void handleFailPayment(String orderId, String message) {
        try {
            Long subscribeId = parseSubscribeId(orderId);

            Payment payment = paymentRepository.findBySubscribeId(subscribeId)
                    .orElseThrow(() -> new RuntimeException("결제 정보가 없습니다."));

            payment.fail();
            paymentRepository.save(payment);
            subscribeService.updateSubscriptionStatus(subscribeId, PaymentStatus.FAILED);

            log.warn("결제 실패 처리됨: orderId={}, subscribeId={}, reason={}", orderId, subscribeId, message);

        } catch (Exception e) {
            log.error("결제 실패 처리 중 오류 발생: orderId={}, error={}", orderId, e.getMessage());
            throw new RuntimeException("결제 실패 처리 중 오류 발생", e);
        }
    }

    public void cancelPayment(String orderId, String cancelReason) {
        Long subscribeId = parseSubscribeId(orderId);

        Payment payment = paymentRepository.findBySubscribeId(subscribeId)
                .orElseThrow(() -> new RuntimeException("결제 정보가 없습니다."));

        if (payment.getStatus() == PaymentStatus.CANCELLED) {
            throw new PaymentFailedException("이미 취소된 결제입니다.");
        }

        if (payment.getPaymentKey() == null) {
            throw new PaymentFailedException("paymentKey가 저장되어 있지 않아 결제를 취소할 수 없습니다.");
        }

        try {
            cancelTossPayment(payment.getPaymentKey(), cancelReason);
            payment.cancel();
            paymentRepository.save(payment);
            subscribeService.updateSubscriptionStatus(subscribeId, PaymentStatus.CANCELLED);

            log.info("결제 취소 완료: orderId={}, subscribeId={}, reason={}", orderId, subscribeId, cancelReason);

        } catch (HttpClientErrorException e) {
            log.error("Toss 결제 취소 실패: orderId={}, error={}", orderId, e.getMessage());
            throw new RuntimeException("결제 취소 실패: " + e.getResponseBodyAsString(), e);
        }
    }

    private void cancelTossPayment(String paymentKey, String reason) {
        String url = "https://api.tosspayments.com/v1/payments/" + paymentKey + "/cancel";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + Base64.getEncoder()
                .encodeToString((secretKey + ":").getBytes()));

        Map<String, Object> body = new HashMap<>();
        body.put("cancelReason", reason);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        restTemplate.postForEntity(url, request, String.class);
    }

    private Long parseSubscribeId(String orderId) {
        try {
            String[] tokens = orderId.split("_");
            if (tokens.length < 2) {
                throw new PaymentFailedException("잘못된 orderId 형식입니다.");
            }
            return Long.parseLong(tokens[1]);
        } catch (NumberFormatException e) {
            throw new PaymentFailedException("orderId에서 subscribeId 추출 실패: 숫자 아님");
        }
    }
}
