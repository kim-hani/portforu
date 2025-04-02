package org.pinggu.portforu.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.domain.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

}
