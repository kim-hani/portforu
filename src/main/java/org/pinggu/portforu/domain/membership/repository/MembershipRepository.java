package org.pinggu.portforu.domain.membership.repository;

import org.pinggu.portforu.domain.membership.entity.Membership;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    @Query("SELECT m FROM Membership m WHERE m.deletedAt IS NULL")
    Page<Membership> findAllActiveMemberships(Pageable pageable);

    Optional<Membership> findByIdAndDeletedAtIsNull(Long membershipId);

}
