package finanze_tracker.app.finance_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import finanze_tracker.app.finance_app.dto.SummaryResponse;
import finanze_tracker.app.finance_app.service.SummaryService;

@RestController
@RequestMapping("/summary")
public class SummaryController {

    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping
    public ResponseEntity<SummaryResponse> getSummary(@RequestParam String month) {
        return ResponseEntity.ok(summaryService.getSummary(month));
    }
}
