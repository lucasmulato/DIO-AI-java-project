package com.lucasmulato.paymentapi.model;

import com.lucasmulato.paymentapi.strategy.PaymentType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    private BigDecimal amount;
    private BigDecimal fee;

    private String status;
    private String description;

    private LocalDateTime createdAt = LocalDateTime.now();

    protected Payment() {}

    public Payment(PaymentType type, BigDecimal amount, BigDecimal fee,
                   String status, String description) {
        this.type = type;
        this.amount = amount;
        this.fee = fee;
        this.status = status;
        this.description = description;
    }

    public Long getId() { return id; }
    public PaymentType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getFee() { return fee; }
    public String getStatus() { return status; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
