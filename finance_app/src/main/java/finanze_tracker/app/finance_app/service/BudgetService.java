package finanze_tracker.app.finance_app.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import finanze_tracker.app.finance_app.dto.BudgetStatusResponse;
import finanze_tracker.app.finance_app.model.Budget;
import finanze_tracker.app.finance_app.model.Transaction;
import finanze_tracker.app.finance_app.repository.BudgetRepository;
import finanze_tracker.app.finance_app.repository.TransactionRepository;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;

    public BudgetService(BudgetRepository budgetRepository, TransactionRepository transactionRepository) {
        this.budgetRepository = budgetRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<BudgetStatusResponse> getBudgetStatus(String month) {

        YearMonth yearMonth = YearMonth.parse(month);
        LocalDate firstOfMonth = yearMonth.atDay(1);

        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime end = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);

        List<Budget> budgets = budgetRepository.findByMonth(firstOfMonth);
        List<Transaction> transactions = transactionRepository.findByOccurredAtBetween(start, end);

        List<BudgetStatusResponse> statusResponses = new ArrayList<>();

        for (Budget budget : budgets) {
            BigDecimal actualSpend = transactions.stream()
                    .filter(t -> t.getCategory().getId().equals(budget.getCategory().getId()))
                    .filter(t -> "EXPENSE".equalsIgnoreCase(t.getCategory().getType()))
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            boolean isOverBudget = actualSpend.compareTo(budget.getLimitAmount()) > 0;

            statusResponses.add(new BudgetStatusResponse(
                    budget.getCategory().getId(),
                    budget.getCategory().getName(),
                    budget.getLimitAmount(),
                    actualSpend,
                    isOverBudget
            ));
        }

        return statusResponses;
    }

}