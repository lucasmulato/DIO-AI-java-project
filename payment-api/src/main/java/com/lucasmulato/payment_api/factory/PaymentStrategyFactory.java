package com.lucasmulato.paymentapi.factory;

import com.lucasmulato.paymentapi.strategy.PaymentStrategy;
import com.lucasmulato.paymentapi.strategy.PaymentType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentStrategyFactory {

    private final Map<PaymentType, PaymentStrategy> strategies;

    public PaymentStrategyFactory(List<PaymentStrategy> strategyList) {
        this.strategies = new EnumMap<>(PaymentType.class);
        strategyList.forEach(strategy ->
                strategies.put(strategy.getType(), strategy));
    }

    public PaymentStrategy getStrategy(PaymentType type) {
        PaymentStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException(
                    "Tipo de pagamento não suportado: " + type);
        }
        return strategy;
    }
}
