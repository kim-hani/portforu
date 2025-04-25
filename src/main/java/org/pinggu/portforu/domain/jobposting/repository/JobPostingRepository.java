package org.pinggu.portforu.domain.jobposting.repository;

import org.pinggu.portforu.domain.jobposting.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    Optional<JobPosting> findByLink(String link);
}
