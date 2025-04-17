package org.pinggu.portforu.web.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentPageViewController {

    @GetMapping("/payments/success")
    public String paymentSuccessView(@RequestParam String orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "payment-success";
    }

    @GetMapping("/payments/fail")
    public String paymentFailView(@RequestParam String message,
                                  @RequestParam Long subscribeId,
                                  Model model) {
        model.addAttribute("message", message);
        model.addAttribute("subscribeId", subscribeId);
        return "payment-fail";
    }

}
