package org.pinggu.portforu.domain.subscribe.repository;

import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    // 페이져블 사용하기 위한 메서드
    Page<Subscribe> findAllByMemberId(Long memberId, Pageable pageable);
}
