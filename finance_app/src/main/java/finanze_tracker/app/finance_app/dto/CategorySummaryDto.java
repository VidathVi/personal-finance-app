package finanze_tracker.app.finance_app.dto;

import java.math.BigDecimal;

public record CategorySummaryDto(
        Long categoryId,
        String categoryName,
        BigDecimal totalAmount) {

}