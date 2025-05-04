package org.pinggu.portforu.domain.scrap.repository;

import org.pinggu.portforu.domain.scrap.entity.Scrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    Optional<Scrap> findByMemberIdAndJobPostingId(Long memberId, Long jobPostingId);

    @EntityGraph(attributePaths = {"jobPosting"})
    Page<Scrap> findAllByMemberIdAndIsDeletedFalse(Long memberId, Pageable pageable);

    @EntityGraph(attributePaths = {"member"})
    List<Scrap> findMemberIdsByJobPostingId(Long jobPostingId);
}
