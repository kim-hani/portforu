package org.pinggu.portforu.domain.subscribe.repository;

import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.membership.entity.Membership;
import org.pinggu.portforu.domain.membership.entity.Membership;
import org.pinggu.portforu.domain.payment.enums.PaymentStatus;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    Page<Subscribe> findAllByMemberAndDeletedAtIsNull(Member member, Pageable pageable);

    @Query("""
    SELECT COUNT(s) FROM Subscribe s
    JOIN Payment p ON p.subscribe = s
    WHERE s.membership = :membership
    AND s.status IN ('ACTIVE', 'CANCELLED') 
    AND s.endDate > :now
    AND p.status = 'COMPLETED'
    AND p.deletedAt IS NULL
""")
    long countActiveByMembership(@Param("membership") Membership membership,
                                 @Param("now") Instant now);

    @Query("""
SELECT COUNT(s) > 0 FROM Subscribe s
JOIN Payment p ON p.subscribe = s
WHERE s.member.id = :memberId
AND s.membership.id = :membershipId
AND s.status IN ('ACTIVE', 'CANCELLED')
AND p.status = 'COMPLETED'
AND s.deletedAt IS NULL
""")
    boolean hasValidSubscription(@Param("memberId") Long memberId,
                                 @Param("membershipId") Long membershipId);


    List<Subscribe> findAllByEndDateBeforeAndDeletedAtIsNull(Instant time);

}