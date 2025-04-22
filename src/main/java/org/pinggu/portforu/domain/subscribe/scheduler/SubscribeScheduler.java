package org.pinggu.portforu.domain.subscribe.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.domain.subscribe.enums.SubscribeStatus;
import org.pinggu.portforu.domain.subscribe.repository.SubscribeRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscribeScheduler {

    private final SubscribeRepository subscribeRepository;

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void expireEndedSubscriptions() {
        Instant now = Instant.now();

        int updatedCount = subscribeRepository.bulkExpireSubscriptions(
                SubscribeStatus.ACTIVE, SubscribeStatus.EXPIRED, now
        );

        log.info("만료된 구독 처리 완료 (벌크 업데이트): count={}", updatedCount);
    }
}