package org.pinggu.portforu.domain.payment.repository;

import org.pinggu.portforu.domain.payment.entity.Payment;
import org.pinggu.portforu.domain.payment.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findBySubscribeId(Long subscribeId);

    @Query("SELECT COUNT(p) > 0 FROM Payment p " +
            "WHERE p.subscribe.member.id = :memberId " +
            "AND p.subscribe.membership.id = :membershipId " +
            "AND p.status = :status")
    boolean existsPayment(@Param("memberId") Long memberId,
                          @Param("membershipId") Long membershipId,
                          @Param("status") PaymentStatus status);

}