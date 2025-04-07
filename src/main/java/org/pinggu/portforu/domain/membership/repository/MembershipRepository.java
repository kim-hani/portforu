package org.pinggu.portforu.domain.membership.repository;

import org.pinggu.portforu.domain.membership.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
}
