package org.pinggu.portforu.domain.jobposting.repository;

import org.pinggu.portforu.domain.jobposting.entity.JobPosting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    Optional<JobPosting> findByIdAndDeletedAtIsNull(Long id);

    Page<JobPosting> findAllByDeletedAtIsNull(Pageable pageable);

}
