package finanze_tracker.app.finance_app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import finanze_tracker.app.finance_app.model.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByMonth(LocalDate month);

}