package org.pinggu.portforu.web.view.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.payment.entity.Payment;
import org.pinggu.portforu.domain.payment.enums.PaymentStatus;
import org.pinggu.portforu.domain.payment.service.PaymentFinder;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.pinggu.portforu.domain.subscribe.service.SubscribeFinder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "결제 페이지 컨트롤러", description = "결제 페이지 렌더링 및 리다이렉트 처리")
@Controller
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentPageController {

    private final SubscribeFinder subscribeFinder;
    private final PaymentFinder paymentFinder;

    @Value("${toss.test-client-key}")
    private String clientKey;

    @Value("${toss.success-url}")
    private String successUrl;

    @Value("${toss.fail-url}")
    private String failUrl;

    @Operation(summary = "결제 페이지 렌더링", description = "구독 ID를 받아 결제 페이지를 렌더링합니다.")
    @GetMapping
    public String paymentPage(
            @RequestParam Long subscribeId,
            Model model
    ) {
        try {
            Subscribe subscribe = subscribeFinder.findById(subscribeId);
            Payment payment = paymentFinder.findBySubscribeId(subscribeId);

            if (payment.getStatus() != PaymentStatus.PENDING) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "만료되었거나 유효하지 않은 결제입니다.");
            }

            Member member = subscribe.getMember();
            model.addAttribute("clientKey", clientKey);
            model.addAttribute("orderId", "order_" + subscribeId + "_" + System.currentTimeMillis());
            model.addAttribute("amount", subscribe.getMembership().getPrice());
            model.addAttribute("orderName", subscribe.getMembership().getName());
            model.addAttribute("customerName", member.getName());
            model.addAttribute("successUrl", successUrl);
            model.addAttribute("failUrl", failUrl);

            return "payment";
        } catch (CustomException e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("subscribeId", subscribeId);
            return "payment-fail";
        } catch (Exception e) {
            model.addAttribute("message", "알 수 없는 오류가 발생했습니다.");
            model.addAttribute("subscribeId", subscribeId);
            return "payment-fail";
        }
    }
}
