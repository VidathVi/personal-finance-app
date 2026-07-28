package finanze_tracker.app.finance_app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import finanze_tracker.app.finance_app.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByOccurredAtBetween(LocalDateTime start, LocalDateTime end);

}
