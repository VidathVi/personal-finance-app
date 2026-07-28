package finanze_tracker.app.finance_app.service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import finanze_tracker.app.finance_app.dto.SummaryResponse;
import finanze_tracker.app.finance_app.model.Transaction;
import finanze_tracker.app.finance_app.repository.AccountRepository;
import finanze_tracker.app.finance_app.repository.BudgetRepository;
import finanze_tracker.app.finance_app.repository.CategoryRepository;
import finanze_tracker.app.finance_app.repository.TransactionRepository;

public class SummaryService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final BudgetRepository budgetRepository;
    private final AccountRepository accountRepository;

    public SummaryService(TransactionRepository transactionRepository, CategoryRepository categoryRepository,
            BudgetRepository budgetRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.budgetRepository = budgetRepository;
        this.accountRepository = accountRepository;
    }

    public SummaryResponse getSummary(String month) {

        YearMonth yearMonth = YearMonth.parse(month);

        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();

        LocalDateTime end = yearMonth.atEndOfMonth().atStartOfDay();

        List<Transaction> transactions = transactionRepository.findByOccurredAtBetween(start, end);

        BigDecimal totalIncome = transactions.stream().filter(t -> t.getAmount() > 0)
                .map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = transactions.stream().filter(t -> t.getAmount() < 0)
                .map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, BigDecimal> categoryBreakdown = transactions.stream()
                .collect(Collectors.groupingBy(t -> t.getCategory().getName(),
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)));

        List<CategorySummaryDto> categorySummary = categoryBreakdown.entrySet().stream()
                .map(e -> new CategorySummaryDto(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        return new SummaryResponse(totalIncome, totalExpense, categorySummary);

    }

}