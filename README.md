https://web.dio.mehttps://web.dio.mehttps://web.dio.me# 💳 Payment API — Padrões de Projeto GoF com Spring Boot

API REST de pagamentos desenvolvida para demonstrar, na prática, a aplicação dos **padrões clássicos GoF** (Gang of Four) integrados às abstrações do **Spring Framework**.

## 🎯 Padrões implementados

| Padrão | Tipo | Onde foi aplicado |
|---|---|---|
| **Strategy** | Comportamental | Tipos de pagamento (Pix, Cartão de Crédito, Boleto) com lógicas e taxas distintas |
| **Factory** | Criacional | `PaymentStrategyFactory` retorna a estratégia correta a partir do `PaymentType` |
| **Singleton** | Criacional | Garantido pelo container Spring (`@Component` / `@Service`) — uma única instância por bean |
| **Facade** | Estrutural | `PaymentFacade` orquestra Factory + Strategy + persistência + eventos em um ponto único |
| **Observer** | Comportamental | Notificações de e-mail e SMS reagindo ao `PaymentCompletedEvent` via `@EventListener` |

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3** (Web, Data JPA)
- **H2 Database** (banco em memória)
- **Maven**

## 🧱 Arquitetura

```
Controller → Facade → Factory → Strategy
                │          └── (Singleton: 1 instância de cada strategy, gerida pelo Spring)
                ├── Repository (H2)
                └── publishEvent(PaymentCompletedEvent)
                        ├── EmailNotificationListener  (@EventListener)
                        └── SmsNotificationListener    (@EventListener)
```

## 📁 Estrutura do projeto

```
src/main/java/com/seunome/paymentapi/
├── PaymentApiApplication.java
├── strategy/
│   ├── PaymentType.java                # Enum com os tipos de pagamento
│   ├── PaymentStrategy.java            # Interface do padrão Strategy
│   ├── PixPaymentStrategy.java         # Taxa zero, compensação imediata
│   ├── CreditCardPaymentStrategy.java  # Taxa de 2,5%
│   └── BoletoPaymentStrategy.java      # Taxa fixa de R$ 3,50
├── factory/
│   └── PaymentStrategyFactory.java     # Resolve a strategy pelo tipo (sem if/else)
├── facade/
│   └── PaymentFacade.java              # Orquestra todo o fluxo de pagamento
├── observer/
│   ├── PaymentCompletedEvent.java      # Evento disparado após o pagamento
│   ├── EmailNotificationListener.java  # Reage ao evento enviando e-mail (mock)
│   └── SmsNotificationListener.java    # Reage ao evento enviando SMS (mock)
├── model/
│   ├── Payment.java                    # Entidade JPA
│   └── PaymentRepository.java          # Repositório Spring Data
└── controller/
    ├── PaymentRequest.java             # DTO de entrada (Java record)
    └── PaymentController.java          # Endpoints REST
```

## ⚙️ Como executar

### Pré-requisitos
- JDK 17+
- Maven (ou use o wrapper `./mvnw` incluso no projeto)

### Rodando a aplicação

```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`.

### Banco H2 (console web)

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:paymentsdb`
- User: `sa` | Password: *(vazio)*

## 📡 Endpoints

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/payments` | Processa um pagamento |
| `GET` | `/payments` | Lista todos os pagamentos |

### 1. Pagamento via Pix (sem taxa)

```bash
curl -X POST http://localhost:8080/payments \
  -H "Content-Type: application/json" \
  -d '{"type": "PIX", "amount": 100.00}'
```

### 2. Pagamento via Cartão de Crédito (taxa 2,5%)

```bash
curl -X POST http://localhost:8080/payments \
  -H "Content-Type: application/json" \
  -d '{"type": "CARTAO_CREDITO", "amount": 200.00}'
```

### 3. Pagamento via Boleto (taxa fixa R$ 3,50)

```bash
curl -X POST http://localhost:8080/payments \
  -H "Content-Type: application/json" \
  -d '{"type": "BOLETO", "amount": 150.00}'
```

### 4. Listar pagamentos

```bash
curl http://localhost:8080/payments
```

### Exemplo de resposta (201 Created)

```json
{
  "id": 1,
  "type": "PIX",
  "amount": 100.00,
  "fee": 0.00,
  "status": "APROVADO",
  "description": "Pagamento via Pix processado no valor de R$ 100. Compensação imediata.",
  "createdAt": "2026-11-14T03:05:12.345"
}
```

Ao processar um pagamento, os **listeners (Observer)** logam automaticamente:

```
[E-MAIL] Enviando confirmação para o cliente | Pagamento #1 | Tipo: PIX | Valor: R$ 100.00
[SMS] Enviando para o cliente | Pagamento #1 aprovado no valor de R$ 100.00
```

## 🧠 Por que esses padrões?

### Strategy — extensibilidade sem modificar código
Cada tipo de pagamento é uma classe que implementa `PaymentStrategy`. Quer adicionar **PayPal**? Crie `PaypalPaymentStrategy`, adicione `PAYPAL` no enum e pronto — nenhuma classe existente muda (*Open/Closed Principle*).

### Factory + Singleton — zero if/else e instâncias únicas
O Spring injeta `List<PaymentStrategy>` na factory automaticamente (cada strategy já é um `@Component`, ou seja, um **Singleton** gerenciado pelo container). O lookup por tipo é feito em um `EnumMap` — sem `switch`, sem `if`.

### Facade — uma porta de entrada
O Controller nunca toca em Strategy, Factory ou Repository. Toda a complexidade fica escondida atrás do `PaymentFacade.processPayment()`.

### Observer — desacoplamento total
A Facade não sabe que existem notificações. Ela apenas publica `PaymentCompletedEvent` e quem estiver ouvindo reage — e-mail, SMS, push... cada um em sua própria classe.

## 🧪 Validações e tratamento de erros

- `type` inválido → `400 Bad Request` ("Tipo de pagamento não suportado")
- `amount` ausente ou menor que zero → `400 Bad Request` (Bean Validation)

*Projeto desenvolvido como trabalho prático de Padrões de Projeto | DIO - Itaú Java com Inteligência Artificial*

