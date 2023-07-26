package hanglog.expense.presentation;

import hanglog.expense.dto.response.ExpenseGetResponse;
import hanglog.expense.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trips/{tripId}/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<ExpenseGetResponse> getExpenses(@PathVariable final long tripId) {
        final ExpenseGetResponse expenseGetResponse = expenseService.getAllExpenses(tripId);
        return ResponseEntity.ok().body(expenseGetResponse);
    }
}
