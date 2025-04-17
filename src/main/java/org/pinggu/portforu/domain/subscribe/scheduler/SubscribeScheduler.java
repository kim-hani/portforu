package org.pinggu.portforu.domain.subscribe.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.pinggu.portforu.domain.subscribe.enums.SubscribeStatus;
import org.pinggu.portforu.domain.subscribe.repository.SubscribeRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscribeScheduler {

    private final SubscribeRepository subscribeRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void expireEndedSubscriptions() {
        Instant now = Instant.now();
        List<Subscribe> expiredSubs = subscribeRepository.findAllByEndDateBeforeAndDeletedAtIsNull(now);

        for (Subscribe sub : expiredSubs) {
            if (sub.getEndDate().isBefore(Instant.now()) && sub.getStatus() != SubscribeStatus.EXPIRED) {
                sub.expire();
                subscribeRepository.save(sub);
            }
        }
    }
}
