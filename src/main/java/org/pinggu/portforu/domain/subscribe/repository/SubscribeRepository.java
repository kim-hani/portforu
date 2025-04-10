package org.pinggu.portforu.domain.subscribe.repository;

import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.membership.entity.Membership;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    @EntityGraph(attributePaths = {"member", "membership"})
    boolean existsByMemberIdAndMembershipId(Long memberId, Long membershipId);

    Page<Subscribe> findAllByMemberAndDeletedAtIsNull(Member member, Pageable pageable);

    long countByMembership(Membership membership);
}