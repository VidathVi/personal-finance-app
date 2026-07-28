package finanze_tracker.app.finance_app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record TransactionRequest(

        @NotNull(message = "account id is required") Long accountId,

        @NotNull(message = "category is required") Long categoryId,

        @NotNull(message = "amount is required") BigDecimal amount,

        @NotNull(message = "date is required") LocalDateTime occurredAt,

        String description) {

}