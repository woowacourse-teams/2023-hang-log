package hanglog.trip.presentation;

import hanglog.trip.dto.response.TripDetailResponse;
import hanglog.trip.dto.response.LedgerResponse;
import hanglog.trip.service.LedgerService;
import hanglog.trip.service.SharedTripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class SharedTripController {

    private final SharedTripService sharedTripService;
    private final LedgerService ledgerService;

    @GetMapping("/shared-trips/{sharedCode}")
    public ResponseEntity<TripDetailResponse> getSharedTrip(@PathVariable final String sharedCode) {
        final Long tripId = sharedTripService.getTripId(sharedCode);
        final TripDetailResponse tripDetailResponse = sharedTripService.getSharedTripDetail(tripId);
        return ResponseEntity.ok().body(tripDetailResponse);
    }

    @GetMapping("/shared-trips/{sharedCode}/expense")
    public ResponseEntity<LedgerResponse> getSharedExpenses(@PathVariable final String sharedCode) {
        final Long tripId = sharedTripService.getTripId(sharedCode);
        final LedgerResponse ledgerResponse = ledgerService.getAllExpenses(tripId);
        return ResponseEntity.ok().body(ledgerResponse);
    }
}
