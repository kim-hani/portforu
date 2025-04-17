package org.pinggu.portforu.domain.payment.controller;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.domain.payment.exception.PaymentFailedException;
import org.pinggu.portforu.domain.payment.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/success")
    public String confirmPayment(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam Long amount,
            RedirectAttributes redirectAttributes
    ) {
        try {
            paymentService.handleSuccessPayment(paymentKey, orderId, amount);
            redirectAttributes.addAttribute("orderId", orderId);
            return "redirect:/payments/success";
        } catch (PaymentFailedException e) {
            Long subscribeId = extractSubscribeIdFromOrderId(orderId);
            redirectAttributes.addAttribute("message", e.getMessage());
            redirectAttributes.addAttribute("subscribeId", subscribeId);
            return "redirect:/payments/fail";
        }
    }

    @GetMapping("/fail")
    public String handlePaymentFail(
            @RequestParam String code,
            @RequestParam String message,
            @RequestParam String orderId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            paymentService.handleFailPayment(orderId, message);
            redirectAttributes.addAttribute("message", message);
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/payments/fail";
    }

    @GetMapping("/cancel")
    public String cancelPayment(@RequestParam String orderId,
                                @RequestParam(defaultValue = "사용자 요청 취소") String reason,
                                RedirectAttributes redirectAttributes) {
        try {
            paymentService.cancelPayment(orderId, reason);
            redirectAttributes.addAttribute("message", "결제가 취소되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", "결제 취소 실패: " + e.getMessage());
        }

        return "redirect:/payments/fail";
    }

    private Long extractSubscribeIdFromOrderId(String orderId) {
        String[] tokens = orderId.split("_");
        return Long.parseLong(tokens[1]);
    }
}
