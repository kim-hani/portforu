package org.pinggu.portforu.domain.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.config.OrderUtils;
import org.pinggu.portforu.domain.payment.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Tag(name = "결제 처리 API", description = "토스 결제 성공/실패/취소 콜백을 처리합니다.")
@Controller
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "결제 성공 처리", description = "토스 결제 성공 후 콜백을 받아 처리합니다.")
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
        } catch (CustomException e) {
            Long subscribeId = OrderUtils.extractSubscribeIdFromOrderId(orderId);
            redirectAttributes.addAttribute("errorCode", e.getMessage());
            redirectAttributes.addAttribute("subscribeId", subscribeId);
            return "redirect:/payments/fail";
        }
    }

    @Operation(summary = "결제 실패 처리", description = "토스 결제 실패 후 콜백을 받아 처리합니다.")
    @GetMapping("/fail")
    public String handlePaymentFail(
            @RequestParam String message,
            @RequestParam String orderId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            paymentService.handleFailPayment(orderId, message);
            redirectAttributes.addAttribute("errorCode", "PAYMENT_FAILED");
        } catch (Exception e) {
            redirectAttributes.addAttribute("errorCode", "INTERNAL_ERROR");
        }

        Long subscribeId = OrderUtils.extractSubscribeIdFromOrderId(orderId);
        redirectAttributes.addAttribute("subscribeId", subscribeId);
        return "redirect:/payments/fail";
    }

    @Operation(summary = "결제 취소 처리", description = "결제 취소 콜백을 받아 처리합니다.")
    @GetMapping("/cancel")
    public String cancelPayment(@RequestParam String orderId,
                                @RequestParam(defaultValue = "사용자 요청 취소") String reason,
                                RedirectAttributes redirectAttributes) {
        try {
            paymentService.cancelPayment(orderId, reason);
            redirectAttributes.addAttribute("errorCode", "PAYMENT_CANCELED");
        } catch (Exception e) {
            redirectAttributes.addAttribute("errorCode", "CANCEL_FAILED");
        }

        Long subscribeId = OrderUtils.extractSubscribeIdFromOrderId(orderId);
        redirectAttributes.addAttribute("subscribeId", subscribeId);
        return "redirect:/payments/fail";
    }
}