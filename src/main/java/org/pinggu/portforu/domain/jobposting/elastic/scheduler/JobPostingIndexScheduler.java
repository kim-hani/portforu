package org.pinggu.portforu.domain.jobposting.elastic.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pinggu.portforu.domain.jobposting.elastic.service.JobPostingSearchService;
import org.pinggu.portforu.domain.jobposting.entity.JobPosting;
import org.pinggu.portforu.domain.jobposting.repository.JobPostingRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobPostingIndexScheduler {

    private final JobPostingRepository jobPostingRepository;
    private final JobPostingSearchService jobPostingSearchService;

    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    @Scheduled(fixedDelay = 180000)
    public void indexUnindexedJobPostings() {
        if (!isRunning.compareAndSet(false, true)) {
            log.warn("색인 중복 실행 방지: 이전 작업이 아직 완료되지 않음");
            return;
        }

        try {
            if (!jobPostingRepository.existsByIndexedFalse()) {
                log.info("색인할 데이터 없음. 스케줄러 종료");
                return;
            }

            List<JobPosting> unindexed = jobPostingRepository.findTop100ByIndexedFalse();

            log.info("색인 시작: {}건", unindexed.size());

            for (JobPosting job : unindexed) {
                try {
                    jobPostingSearchService.index(job);
                    job.markAsIndexed();
                } catch (Exception e) {
                    log.error("색인 실패: {}", job.getId(), e);
                }
            }

            jobPostingRepository.saveAll(unindexed);

            log.info("색인 완료");
        } catch (Exception ex) {
            log.error("색인 스케줄러 오류 발생", ex);
        } finally {
            isRunning.set(false);
        }
    }
}



