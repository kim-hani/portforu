package org.pinggu.portforu.domain.membership.repository;

import org.pinggu.portforu.domain.membership.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    @Query("SELECT m FROM Membership m WHERE m.isDeleted IS NULL")
    List<Membership> findAllActiveMemberships();

}
