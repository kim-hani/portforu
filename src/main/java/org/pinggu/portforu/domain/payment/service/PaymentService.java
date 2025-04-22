package org.pinggu.portforu.domain.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.membership.entity.Membership;
import org.pinggu.portforu.domain.payment.dto.response.TossPaymentConfirmResponseDto;
import org.pinggu.portforu.domain.payment.entity.Payment;
import org.pinggu.portforu.domain.payment.enums.PaymentMethod;
import org.pinggu.portforu.domain.payment.enums.PaymentStatus;
import org.pinggu.portforu.domain.payment.repository.PaymentRepository;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.pinggu.portforu.domain.subscribe.repository.SubscribeRepository;
import org.pinggu.portforu.domain.subscribe.service.SubscribeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final RestTemplate restTemplate;
    private final PaymentRepository paymentRepository;
    private final PaymentFinder paymentFinder;
    private final SubscribeService subscribeService;
    private final SubscribeRepository subscribeRepository;

    @Value("${toss.test-secret-key}")
    private String secretKey;

    public void handleSuccessPayment(String paymentKey, String orderId, Long amount) {
        Long subscribeId = parseSubscribeId(orderId);
        try {
            Payment payment = paymentFinder.findBySubscribeId(subscribeId);

            if (payment.getStatus() == PaymentStatus.COMPLETED) {
                //TODO 이건 동시성 어떻게 할건지, 서드파티 이용하는 거기 때문에 문제가 생길 여지가 있음
                // -redis 도입해서 분산 lock처리를 해주면 괜찮을거같아서 이렇게 해둿는데 고치고 redis를 넣고 할까요?
                log.info("중복 결제 요청 차단됨: orderId={}, subscribeId={}", orderId, subscribeId);
                throw new CustomException(HttpStatus.BAD_REQUEST, "이미 결제가 완료된 주문입니다.");
            }

            Subscribe subscribe = payment.getSubscribe();
            Membership membership = subscribe.getMembership();

            //  정원 수량으로 체크 (count 방식 제거) 동시성문제는 나중에 redis 사용할예정
            if (membership.getQuantity() <= 0) {
                try {
                    cancelTossPayment(paymentKey, "멤버십 정원이 초과되어 결제가 취소되었습니다.");
                } catch (Exception e) {
                    log.warn("Toss 결제 취소 실패: {}", e.getMessage());
                }

                payment.fail();
                subscribe.fail();
                paymentRepository.save(payment);
                subscribeRepository.save(subscribe);

                log.warn("결제 실패 - 정원 초과: orderId={}, subscribeId={}", orderId, subscribeId);
                throw new CustomException(HttpStatus.BAD_REQUEST, "멤버십 정원이 초과되어 결제가 취소되었습니다.");
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
            ResponseEntity<TossPaymentConfirmResponseDto> response = restTemplate
                    .postForEntity(url, request, TossPaymentConfirmResponseDto.class);

            String method = response.getBody() != null ? response.getBody().getMethod() : null;
            log.info("Toss에서 받은 결제 수단: {}", method);
            PaymentMethod paymentMethod = PaymentMethod.fromTossMethod(method);

            payment.assignPaymentKey(paymentKey);
            payment.assignPaymentMethod(paymentMethod);
            payment.complete();

            subscribeService.updateSubscriptionStatus(subscribeId, PaymentStatus.COMPLETED);

            log.info("결제 완료: orderId={}, subscribeId={}, amount={}", orderId, subscribeId, amount);

        } catch (HttpClientErrorException e) {
            Subscribe subscribe = subscribeRepository.findById(subscribeId)
                    .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "구독 정보를 찾을 수 없습니다."));
            subscribe.fail();
            subscribeRepository.save(subscribe);

            log.warn("Toss 결제 승인 실패: orderId={}, subscribeId={}, error={}", orderId, subscribeId, e.getMessage());

            throw new CustomException(HttpStatus.BAD_REQUEST, "Toss 결제 승인 실패: " + e.getMessage());
        }
    }

    public void handleFailPayment(String orderId, String message) {
        try {
            Long subscribeId = parseSubscribeId(orderId);

            Payment payment = paymentFinder.findBySubscribeId(subscribeId);

            payment.fail();
            subscribeService.updateSubscriptionStatus(subscribeId, PaymentStatus.FAILED);

            log.warn("결제 실패 처리됨: orderId={}, subscribeId={}, reason={}", orderId, subscribeId, message);

        } catch (Exception e) {
            log.error("결제 실패 처리 중 오류 발생: orderId={}, error={}", orderId, e.getMessage());
            throw new RuntimeException("결제 실패 처리 중 오류 발생", e);
        }
    }

    public void cancelPayment(String orderId, String cancelReason) {
        Long subscribeId = parseSubscribeId(orderId);

        Payment payment = paymentFinder.findBySubscribeId(subscribeId);

        if (payment.getStatus() == PaymentStatus.CANCELED) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 취소된 결제입니다.");
        }

        if (payment.getPaymentKey() == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "paymentKey가 저장되어 있지 않아 결제를 취소할 수 없습니다.");
        }

        try {
            cancelTossPayment(payment.getPaymentKey(), cancelReason);
            payment.cancel();
            subscribeService.updateSubscriptionStatus(subscribeId, PaymentStatus.CANCELED);

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
                throw new CustomException(HttpStatus.BAD_REQUEST, "잘못된 orderId 형식입니다.");
            }
            return Long.parseLong(tokens[1]);
        } catch (NumberFormatException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "orderId에서 subscribeId 추출 실패: 숫자 아님");
        }
    }
}
