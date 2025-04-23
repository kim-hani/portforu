package org.pinggu.portforu.common.lock;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisLockExecutor {

    private final RedissonClient redissonClient;

    public void executeWithLock(String key, long waitTime, long leaseTime, Runnable task) {
        RLock lock = redissonClient.getLock(key);
        boolean isLocked = false;

        try {
            isLocked = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            if (isLocked) {
                task.run(); // 락 잡혔으면 로직 실행
            } else {
                throw new IllegalStateException("락 획득 실패: key=" + key);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("락 대기 중 인터럽트 발생", e);
        } finally {
            if (isLocked && lock.isHeldByCurrentThread()) {
                lock.unlock(); // 락 해제
            }
        }
    }
}

