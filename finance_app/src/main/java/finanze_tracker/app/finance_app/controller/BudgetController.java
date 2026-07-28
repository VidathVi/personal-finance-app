package finanze_tracker.app.finance_app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import finanze_tracker.app.finance_app.dto.BudgetStatusResponse;
import finanze_tracker.app.finance_app.service.BudgetService;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping("/status")
    public ResponseEntity<List<BudgetStatusResponse>> getBudgetStatus(@RequestParam String month) {
        return ResponseEntity.ok(budgetService.getBudgetStatus(month));
    }
}
