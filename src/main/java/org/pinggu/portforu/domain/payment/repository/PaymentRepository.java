package org.pinggu.portforu.domain.payment.repository;

import org.pinggu.portforu.domain.payment.entity.Payment;
import org.pinggu.portforu.domain.payment.enums.PaymentStatus;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findBySubscribe(Subscribe subscribe);

    Optional<Payment> findBySubscribeId(Long subscribeId);

    boolean existsBySubscribe_Member_IdAndSubscribe_Membership_IdAndStatus(Long memberId, Long membershipId, PaymentStatus paymentStatus);

    List<Payment> findByStatusAndCreatedAtBefore(PaymentStatus status, Instant time);

}
