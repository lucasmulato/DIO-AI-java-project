package com.lucasmulato.paymentapi.observer;

import com.lucasmulato.paymentapi.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationListener {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationListener.class);

    @EventListener
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        Payment payment = event.payment();
        log.info("[E-MAIL] Enviando confirmação para o cliente | "
                + "Pagamento #{} | Tipo: {} | Valor: R$ {}",
                payment.getId(), payment.getType(), payment.getAmount());
    }
}
