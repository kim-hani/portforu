package org.pinggu.portforu.domain.member.repository;

import org.pinggu.portforu.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    Page<Member> findAllByDeletedAtIsNull(Pageable pageable);
}
