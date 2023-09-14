package hanglog.expense.presentation;

import hanglog.auth.Auth;
import hanglog.auth.domain.Accessor;
import hanglog.expense.dto.response.TripExpenseResponse;
import hanglog.expense.service.ExpenseService;
import hanglog.trip.service.TripService;
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
    private final TripService tripService;

    @GetMapping
    public ResponseEntity<TripExpenseResponse> getExpenses(@Auth final Accessor accessor,
                                                           @PathVariable final Long tripId) {
        tripService.validateTripByMember(accessor.getMemberId(), tripId);
        final TripExpenseResponse tripExpenseResponse = expenseService.getAllExpenses(tripId);
        return ResponseEntity.ok().body(tripExpenseResponse);
    }
}
