package hanglog.trip.presentation;

import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.dto.request.TripUpdateRequest;
import hanglog.trip.dto.response.TripDetailResponse;
import hanglog.trip.dto.response.TripResponse;
import hanglog.trip.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    @PostMapping
    public ResponseEntity<Void> createTrip(@RequestBody @Valid final TripCreateRequest tripCreateRequest) {
        final Long tripId = tripService.save(tripCreateRequest);
        return ResponseEntity.created(URI.create("/trips/" + tripId)).build();
    }

    @GetMapping
    public ResponseEntity<List<TripResponse>> getTrips() {
        List<TripResponse> tripResponses = tripService.getAllTrips();
        return ResponseEntity.ok().body(tripResponses);
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<TripDetailResponse> getTrip(@PathVariable final Long tripId) {
        TripDetailResponse tripDetailResponse = tripService.getTripDetail(tripId);
        return ResponseEntity.ok().body(tripDetailResponse);
    }

    @PutMapping("/{tripId}")
    public ResponseEntity<Void> updateTrip(
            @PathVariable final Long tripId,
            @RequestBody @Valid final TripUpdateRequest updateRequest
    ) {
        tripService.update(tripId, updateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<Void> deleteTrip(@PathVariable final Long tripId) {
        tripService.delete(tripId);
        return ResponseEntity.noContent().build();
    }
}
