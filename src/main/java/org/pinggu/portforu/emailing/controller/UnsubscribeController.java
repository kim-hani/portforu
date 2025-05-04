package org.pinggu.portforu.emailing.controller;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.emailing.service.UnsubscribeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emails/unsubscribe")
public class UnsubscribeController {

    private final UnsubscribeService unsubscribeService;

    @GetMapping
    public ResponseEntity<String> unsubscribe(@RequestParam("token") String token) {
        unsubscribeService.unsubscribe(token); // 서비스 호출
        return ResponseEntity.ok("구독이 성공적으로 해지되었습니다."); // 이부분 추후 html 리다이렉트로 해도 될 것 같습니다.
    }
}
