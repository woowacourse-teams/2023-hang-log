package hanglog.trip.presentation;

import hanglog.auth.Auth;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.dto.request.TripUpdateRequest;
import hanglog.trip.dto.response.TripDetailResponse;
import hanglog.trip.dto.response.TripResponse;
import hanglog.trip.service.TripService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    @PostMapping
    public ResponseEntity<Void> createTrip(
            @Auth final Long memberId,
            @RequestBody @Valid final TripCreateRequest tripCreateRequest
    ) {
        final Long tripId = tripService.save(memberId, tripCreateRequest);
        return ResponseEntity.created(URI.create("/trips/" + tripId)).build();
    }

    @GetMapping
    public ResponseEntity<List<TripResponse>> getTrips(@Auth final Long memberId) {
        final List<TripResponse> tripResponses = tripService.getAllTrips(memberId);
        return ResponseEntity.ok().body(tripResponses);
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<TripDetailResponse> getTrip(@Auth final Long memberId, @PathVariable final Long tripId) {
        tripService.validateTripByMember(memberId, tripId);
        final TripDetailResponse tripDetailResponse = tripService.getTripDetail(tripId);
        return ResponseEntity.ok().body(tripDetailResponse);
    }

    @PutMapping("/{tripId}")
    public ResponseEntity<Void> updateTrip(
            @Auth final Long memberId,
            @PathVariable final Long tripId,
            @RequestBody @Valid final TripUpdateRequest updateRequest
    ) {
        tripService.validateTripByMember(memberId, tripId);
        tripService.update(tripId, updateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<Void> deleteTrip(@Auth final Long memberId, @PathVariable final Long tripId) {
        tripService.validateTripByMember(memberId, tripId);
        tripService.delete(tripId);
        return ResponseEntity.noContent().build();
    }
}
