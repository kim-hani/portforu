package org.pinggu.portforu.domain.payment.repository;

import org.pinggu.portforu.domain.payment.entity.Payment;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findBySubscribe(Subscribe subscribe);
}
