package hanglog.share.presentation;

import hanglog.auth.Auth;
import hanglog.auth.domain.Accessor;
import hanglog.expense.dto.response.TripExpenseResponse;
import hanglog.expense.service.ExpenseService;
import hanglog.share.dto.request.SharedTripStatusRequest;
import hanglog.share.dto.response.SharedTripCodeResponse;
import hanglog.share.service.SharedTripService;
import hanglog.trip.dto.response.TripDetailResponse;
import hanglog.trip.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class SharedTripController {

    private final SharedTripService sharedTripService;
    private final TripService tripService;
    private final ExpenseService expenseService;

    @GetMapping("/shared-trips/{sharedCode}")
    public ResponseEntity<TripDetailResponse> getSharedTrip(@PathVariable final String sharedCode) {
        final Long tripId = sharedTripService.getTripId(sharedCode);
        final TripDetailResponse tripDetailResponse = tripService.getTripDetail(tripId);
        return ResponseEntity.ok().body(tripDetailResponse);
    }

    @PatchMapping("/trips/{tripId}/share")
    public ResponseEntity<SharedTripCodeResponse> updateSharedStatus(
            @Auth final Accessor accessor,
            @PathVariable final Long tripId,
            @RequestBody @Valid final SharedTripStatusRequest sharedTripStatusRequest
    ) {
        tripService.validateTripByMember(accessor.getMemberId(), tripId);
        final SharedTripCodeResponse sharedTripCodeResponse = sharedTripService.updateSharedTripStatus(
                tripId,
                sharedTripStatusRequest
        );
        return ResponseEntity.ok().body(sharedTripCodeResponse);
    }

    @GetMapping("/shared-trips/{sharedCode}/expense")
    public ResponseEntity<TripExpenseResponse> getSharedExpenses(@PathVariable final String sharedCode) {
        final Long tripId = sharedTripService.getTripId(sharedCode);
        final TripExpenseResponse tripExpenseResponse = expenseService.getAllExpenses(tripId);
        return ResponseEntity.ok().body(tripExpenseResponse);
    }
}
