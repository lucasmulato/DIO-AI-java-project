package com.lucasmulato.paymentapi.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PixPaymentStrategy implements PaymentStrategy {

    @Override
    public PaymentType getType() {
        return PaymentType.PIX;
    }

    @Override
    public String processPayment(BigDecimal amount) {
        return "Pagamento via Pix processado no valor de R$ " + amount
                + ". Compensação imediata.";
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
