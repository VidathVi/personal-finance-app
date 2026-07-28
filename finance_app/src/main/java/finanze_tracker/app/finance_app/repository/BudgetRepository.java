package finanze_tracker.app.finance_app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import finanze_tracker.app.finance_app.model.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    @Query("SELECT * FROM budgets WHERE month = :month")
    List<Budget> findByMonth(@Param("month") LocalDate month);

}