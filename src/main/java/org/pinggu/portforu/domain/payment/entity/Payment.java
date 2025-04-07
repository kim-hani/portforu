package org.pinggu.portforu.domain.payment.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // 결제 수단

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // 결제 상태

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscribe_id", nullable = false)
    private Subscribe subscribe;

    @Builder
    public Payment(PaymentMethod paymentMethod,PaymentStatus status,Subscribe subscribe) {
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.subscribe = subscribe;
    }

    public enum PaymentMethod {
        CREDIT_CARD, DEBIT_CARD, PAYPAL, OTHER
    }

    public enum PaymentStatus {
        PENDING, COMPLETED, FAILED
    }
}

