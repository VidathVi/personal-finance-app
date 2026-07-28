package finanze_tracker.app.finance_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import finanze_tracker.app.finance_app.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}