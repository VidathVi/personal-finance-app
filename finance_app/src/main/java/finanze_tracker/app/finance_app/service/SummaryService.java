package finanze_tracker.app.finance_app.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import finanze_tracker.app.finance_app.dto.CategorySummaryDto;
import finanze_tracker.app.finance_app.dto.SummaryResponse;
import finanze_tracker.app.finance_app.model.Category;
import finanze_tracker.app.finance_app.model.Transaction;
import finanze_tracker.app.finance_app.repository.TransactionRepository;

@Service
public class SummaryService {

    private final TransactionRepository transactionRepository;

    public SummaryService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public SummaryResponse getSummary(String month) {

        YearMonth yearMonth = YearMonth.parse(month);
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime end = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);

        List<Transaction> transactions = transactionRepository.findByOccurredAtBetween(start, end);

        BigDecimal totalIncome = transactions.stream()
                .filter(t -> "INCOME".equalsIgnoreCase(t.getCategory().getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = transactions.stream()
                .filter(t -> "EXPENSE".equalsIgnoreCase(t.getCategory().getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<Category, BigDecimal> categoryBreakdown = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
                ));

        List<CategorySummaryDto> categorySummary = categoryBreakdown.entrySet().stream()
                .map(e -> new CategorySummaryDto(e.getKey().getId(), e.getKey().getName(), e.getValue()))
                .collect(Collectors.toList());

        return new SummaryResponse(totalIncome, totalExpense, categorySummary);
    }

}