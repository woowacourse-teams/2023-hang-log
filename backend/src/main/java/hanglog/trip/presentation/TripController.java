package hanglog.trip.presentation;

import hanglog.auth.Auth;
import hanglog.auth.MemberOnly;
import hanglog.auth.domain.Accessor;
import hanglog.trip.dto.PublishedStatusRequest;
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
import org.springframework.web.bind.annotation.PatchMapping;
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
    @MemberOnly
    public ResponseEntity<Void> createTrip(
            @Auth final Accessor accessor,
            @RequestBody @Valid final TripCreateRequest tripCreateRequest
    ) {
        final Long tripId = tripService.save(accessor.getMemberId(), tripCreateRequest);
        return ResponseEntity.created(URI.create("/trips/" + tripId)).build();
    }

    @GetMapping
    @MemberOnly
    public ResponseEntity<List<TripResponse>> getTrips(@Auth final Accessor accessor) {
        final List<TripResponse> tripResponses = tripService.getAllTrips(accessor.getMemberId());
        return ResponseEntity.ok().body(tripResponses);
    }

    @GetMapping("/{tripId}")
    @MemberOnly
    public ResponseEntity<TripDetailResponse> getTrip(@Auth final Accessor accessor, @PathVariable final Long tripId) {
        tripService.validateTripByMember(accessor.getMemberId(), tripId);
        final TripDetailResponse tripDetailResponse = tripService.getTripDetail(tripId);
        return ResponseEntity.ok().body(tripDetailResponse);
    }

    @PutMapping("/{tripId}")
    @MemberOnly
    public ResponseEntity<Void> updateTrip(
            @Auth final Accessor accessor,
            @PathVariable final Long tripId,
            @RequestBody @Valid final TripUpdateRequest updateRequest
    ) {
        tripService.validateTripByMember(accessor.getMemberId(), tripId);
        tripService.update(tripId, updateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{tripId}")
    @MemberOnly
    public ResponseEntity<Void> deleteTrip(@Auth final Accessor accessor, @PathVariable final Long tripId) {
        tripService.validateTripByMember(accessor.getMemberId(), tripId);
        tripService.delete(tripId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{tripId}/publish")
    public ResponseEntity<Void> updatePublishedStatus(
            @Auth final Accessor accessor,
            @PathVariable final Long tripId,
            @RequestBody @Valid final PublishedStatusRequest publishedStatusRequest
    ) {
        tripService.validateTripByMember(accessor.getMemberId(), tripId);
        tripService.updatePublishedStatus(tripId, publishedStatusRequest);
        return ResponseEntity.noContent().build();
    }
}
