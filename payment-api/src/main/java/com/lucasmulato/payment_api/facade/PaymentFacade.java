package com.lucasmulato.paymentapi.facade;

import com.lucasmulato.paymentapi.factory.PaymentStrategyFactory;
import com.lucasmulato.paymentapi.model.Payment;
import com.lucasmulato.paymentapi.model.PaymentRepository;
import com.lucasmulato.paymentapi.observer.PaymentCompletedEvent;
import com.lucasmulato.paymentapi.strategy.PaymentStrategy;
import com.lucasmulato.paymentapi.strategy.PaymentType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentFacade {

    private final PaymentStrategyFactory strategyFactory;
    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher eventPublisher;

    public PaymentFacade(PaymentStrategyFactory strategyFactory,
                         PaymentRepository paymentRepository,
                         ApplicationEventPublisher eventPublisher) {
        this.strategyFactory = strategyFactory;
        this.paymentRepository = paymentRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Payment processPayment(PaymentType type, BigDecimal amount) {
        PaymentStrategy strategy = strategyFactory.getStrategy(type);

        String description = strategy.processPayment(amount);
        BigDecimal fee = strategy.calculateFee(amount);

        Payment payment = new Payment(type, amount, fee, "APROVADO", description);
        Payment saved = paymentRepository.save(payment);

        eventPublisher.publishEvent(new PaymentCompletedEvent(saved));

        return saved;
    }
    public List<Payment> listAll() {
        return paymentRepository.findAll();
}
}
