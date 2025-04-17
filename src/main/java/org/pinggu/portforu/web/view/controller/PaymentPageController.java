package org.pinggu.portforu.web.view.controller;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.pinggu.portforu.domain.subscribe.service.SubscribeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pay")
public class PaymentPageController {

    private final SubscribeService subscribeService;

    @Value("${toss.test-client-key}")
    private String clientKey;

    @Value("${toss.success-url}")
    private String successUrl;

    @Value("${toss.fail-url}")
    private String failUrl;

    @GetMapping
    public String paymentPage(@RequestParam Long subscribeId, Model model) {

        try {
            Subscribe subscribe = subscribeService.findById(subscribeId);
            Member member = subscribe.getMember();

            model.addAttribute("clientKey", clientKey);
            String orderId = "order_" + subscribeId + "_" + System.currentTimeMillis();
            model.addAttribute("orderId", orderId);
            model.addAttribute("amount", subscribe.getMembership().getPrice());
            model.addAttribute("orderName", subscribe.getMembership().getName());
            model.addAttribute("customerName", member.getName());
            model.addAttribute("successUrl", successUrl);
            model.addAttribute("failUrl", failUrl);

            return "payment";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}

