package org.pinggu.portforu.web.view.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "결제 결과 뷰 API", description = "결제 성공·실패 뷰 화면을 렌더링합니다.")
@Controller
public class PaymentPageViewController {

    @Operation(summary = "결제 성공 화면", description = "결제 성공 후 결과 화면을 렌더링합니다.")
    @GetMapping("/payments/success")
    public String paymentSuccessView(@RequestParam String orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "payment-success";
    }

    @Operation(summary = "결제 실패 화면", description = "결제 실패 후 결과 화면을 렌더링합니다.")
    @GetMapping("/payments/fail")
    public String paymentFailView(@RequestParam("errorCode") String errorCode,
                                  @RequestParam Long subscribeId,
                                  Model model) {
        model.addAttribute("message", errorCode);
        model.addAttribute("subscribeId", subscribeId);
        return "payment-fail";
    }

}
