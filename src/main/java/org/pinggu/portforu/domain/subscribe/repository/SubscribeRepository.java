package org.pinggu.portforu.domain.subscribe.repository;

import org.pinggu.portforu.domain.member.entity.Member;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    Page<Subscribe> findAllByMember(Member member, Pageable pageable);

}