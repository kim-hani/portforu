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

// 책임 분리를 위해
@Slf4j
@Component
@RequiredArgsConstructor
public class CanceledSubscribeScheduler {

    private final SubscribeRepository subscribeRepository;
    private final RedisLockExecutor redisLockExecutor;
    private final JdbcTemplate jdbcTemplate;

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void expireCanceledSubscriptions() {
        redisLockExecutor.executeWithLock("lock:expire-canceled-subscriptions", 10, 3,
                () -> {
                    Instant now = Instant.now();

                    List<Subscribe> canceledSubs = subscribeRepository
                            .findAllByStatusAndEndDateBefore(SubscribeStatus.CANCELED, now);

                    canceledSubs.forEach(sub -> {
                        sub.expire();  // CANCELED → EXPIRED

                        String sql = """
                        UPDATE memberships
                        SET quantity = quantity + 1
                        WHERE id = (
                            SELECT membership_id FROM subscribes WHERE id = ?
                        )
                    """;
                        jdbcTemplate.update(sql, sub.getId());
                    });

                    log.info("만료된 CANCELED 구독 처리 및 정원 복구 완료: count={}", canceledSubs.size());
                }
        );
    }
}
