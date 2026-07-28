package finanze_tracker.app.finance_app.dto;

import java.math.BigDecimal;
import java.util.List;

public record SummaryResponse(
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        List<CategorySummaryDto> categoryBreakdown) {

}