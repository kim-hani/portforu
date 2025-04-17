package org.pinggu.portforu.domain.payment.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pinggu.portforu.common.domain.BaseEntity;
import org.pinggu.portforu.domain.payment.enums.PaymentMethod;
import org.pinggu.portforu.domain.payment.enums.PaymentStatus;
import org.pinggu.portforu.domain.subscribe.entity.Subscribe;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscribe_id", nullable = false)
    private Subscribe subscribe;

    @Column(name = "payment_key")
    private String paymentKey;

    @Builder
    public Payment(PaymentMethod paymentMethod, PaymentStatus status, Subscribe subscribe) {
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.subscribe = subscribe;
    }

    public void assignPaymentKey(String key) {
        if (this.paymentKey != null) {
            throw new IllegalStateException("이미 paymentKey가 설정되어 있습니다.");
        }
        this.paymentKey = key;
    }

    public void complete() {
        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalStateException("결제는 PENDING 상태에서만 완료할 수 있습니다.");
        }
        this.status = PaymentStatus.COMPLETED;
    }

    public void fail() {
        this.status = PaymentStatus.FAILED;
    }

    public void expire() {
        this.status = PaymentStatus.EXPIRED;
    }


    public void cancel() {
        if (this.status != PaymentStatus.COMPLETED) {
            throw new IllegalStateException("결제가 완료된 상태에서만 취소할 수 있습니다.");
        }
        this.status = PaymentStatus.CANCELLED;
    }

}
