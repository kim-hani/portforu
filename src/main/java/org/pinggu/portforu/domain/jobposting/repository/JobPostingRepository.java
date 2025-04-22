package org.pinggu.portforu.domain.jobposting.repository;

import org.pinggu.portforu.domain.jobposting.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

}
