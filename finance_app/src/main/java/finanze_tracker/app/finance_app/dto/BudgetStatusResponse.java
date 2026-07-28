package finanze_tracker.app.finance_app.dto;

import java.math.BigDecimal;

public record BudgetStatusResponse(
        Long categoryId,
        String categoryName,
        BigDecimal limitAmount,
        BigDecimal actualSpend,
        boolean isOverBudget) {

}