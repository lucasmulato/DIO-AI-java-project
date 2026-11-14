package com.lucasmulato.paymentapi.observer;

import com.lucasmulato.paymentapi.model.Payment;

public record PaymentCompletedEvent(Payment payment) {
}
