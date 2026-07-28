package finanze_tracker.app.finance_app.dto;

public record BudgetStatusResponse(
        long categoryId,
        String categoryName,
        double limitAmount,
        double actualSpend,
        boolean isOverBudget) {

}