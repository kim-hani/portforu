package org.pinggu.portforu.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.pinggu.portforu.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

}
