package org.pinggu.portforu.domain.health.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    // Elastic Beanstalk에서 HealthCheck

    @GetMapping("/")
    public String healthCheck() {
        return "OK";    // 200OK 받으면 서버 살아있음
    }



}
