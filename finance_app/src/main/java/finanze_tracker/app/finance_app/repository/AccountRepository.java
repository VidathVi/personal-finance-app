package finanze_tracker.app.finance_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import finanze_tracker.app.finance_app.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}