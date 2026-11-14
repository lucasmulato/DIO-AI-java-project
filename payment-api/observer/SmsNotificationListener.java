package com.lucasmulato.paymentapi.observer;

import com.lucasmulato.paymentapi.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SmsNotificationListener {

    private static final Logger log = LoggerFactory.getLogger(SmsNotificationListener.class);

    @EventListener
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        Payment payment = event.payment();
        log.info("[SMS] Enviando para o cliente | Pagamento #{} aprovado no valor de R$ {}",
                payment.getId(), payment.getAmount());
    }
}
