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
        if (!jobPostingRepository.existsByIndexedFalse()) {
            log.info("색인할 데이터 없음. 스케줄러 종료");
            return;
        }

        if (!isRunning.compareAndSet(false, true)) {
            log.warn("색인 중복 실행 방지: 이전 작업이 아직 완료되지 않음");
            return;
        }

        try {
            List<JobPosting> unindexed = jobPostingRepository.findTop100ByIndexedFalse();

            for (JobPosting job : unindexed) {
                try {
                    jobPostingSearchService.index(job);
                    job.markAsIndexed();
                    jobPostingRepository.save(job);
                    log.info("색인 완료: {}", job.getId());
                } catch (Exception e) {
                    log.error("색인 실패: {}", job.getId(), e);
                }
            }

        } finally {
            isRunning.set(false);
        }
    }
}



