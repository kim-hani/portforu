package org.pinggu.portforu.domain.auth.verify;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class PhoneVerification {

    private final StringRedisTemplate redisTemplate;

    private static final String CODE_PREFIX = "CODE:";
    private static final String VERIFIED_PREFIX = "VERIFIED:";
    private static final Duration CODE_TTL = Duration.ofMinutes(3);
    private static final Duration VERIFIED_TTL = Duration.ofMinutes(10);

    public void saveCode(String phoneNumber, String code) {
        redisTemplate.opsForValue().set(CODE_PREFIX + phoneNumber, code, CODE_TTL);
    }

    public String getCode(String phoneNumber) {
        return redisTemplate.opsForValue().get(CODE_PREFIX + phoneNumber);
    }

    public void deleteCode(String phoneNumber) {
        redisTemplate.delete(CODE_PREFIX + phoneNumber);
    }

    public void markVerified(String phoneNumber) {
        redisTemplate.opsForValue().set(VERIFIED_PREFIX + phoneNumber, "true", VERIFIED_TTL);
    }

    public boolean isVerified(String phoneNumber) {
        return "true".equals(redisTemplate.opsForValue().get(VERIFIED_PREFIX + phoneNumber));
    }
}
