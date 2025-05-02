package org.pinggu.portforu.domain.subscribe.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.common.lock.RedisLockExecutor;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.pinggu.portforu.domain.subscribe.enums.SubscribeStatus;
import org.pinggu.portforu.domain.subscribe.repository.SubscribeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscribeScheduler {

    private final SubscribeRepository subscribeRepository;
    private final RedisLockExecutor redisLockExecutor;
    private final JdbcTemplate jdbcTemplate;

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void expireEndedSubscriptions() {
        redisLockExecutor.executeWithLock("lock:expire-subscriptions", 10, 3, () -> {
            Instant now = Instant.now();

            List<Subscribe> activeSubs = subscribeRepository
                    .findAllByStatusAndEndDateBefore(SubscribeStatus.ACTIVE, now);

            activeSubs.forEach(sub -> {
                sub.expire();

                String sql = """
                UPDATE memberships
                SET quantity = quantity + 1
                WHERE id = (
                    SELECT membership_id FROM subscribes WHERE id = ?
                )
            """;
                jdbcTemplate.update(sql, sub.getId());
            });

            log.info("만료된 ACTIVE 구독 처리 완료: count={}", activeSubs.size());
        });
    }
}