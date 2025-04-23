package org.pinggu.portforu.domain.subscribe.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.domain.subscribe.enums.SubscribeStatus;
import org.pinggu.portforu.domain.subscribe.repository.SubscribeRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
// 책임 분리를 위해
@Slf4j
@Component
@RequiredArgsConstructor
public class CanceledSubscribeScheduler {

    private final SubscribeRepository subscribeRepository;

    @Scheduled(cron = "0 0 3 * * *") // 새벽 3시
    @Transactional
    public void expireCanceledSubscriptions() {
        Instant now = Instant.now();

        int updatedCount = subscribeRepository.bulkExpireSubscriptions(
                SubscribeStatus.CANCELED, SubscribeStatus.EXPIRED, now
        );

        log.info("취소된 구독 중 만료된 것 처리 완료: count={}", updatedCount);
    }
}
