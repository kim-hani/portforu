package org.pinggu.portforu.domain.scrap.repository;

import org.pinggu.portforu.domain.jobposting.entity.JobPosting;
import org.pinggu.portforu.domain.scrap.entity.Scrap;
import org.pinggu.portforu.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    @EntityGraph(attributePaths = {"member", "jobPosting"})
    Optional<Scrap> findByMemberAndJobPosting(Member member, JobPosting jobPosting);

    @EntityGraph(attributePaths = {"member", "jobPosting"})
    Page<Scrap> findAllByMemberIdAndDeletedAtIsNull(Long memberId, Pageable pageable);

}
