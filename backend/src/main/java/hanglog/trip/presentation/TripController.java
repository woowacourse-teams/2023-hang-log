package hanglog.trip.presentation;

import hanglog.auth.Auth;
import hanglog.auth.MemberOnly;
import hanglog.auth.domain.Accessor;
import hanglog.trip.dto.request.PublishedStatusRequest;
import hanglog.trip.dto.request.SharedStatusRequest;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.dto.request.TripUpdateRequest;
import hanglog.trip.dto.response.LedgerResponse;
import hanglog.trip.dto.response.SharedCodeResponse;
import hanglog.trip.dto.response.TripDetailResponse;
import hanglog.trip.dto.response.TripResponse;
import hanglog.trip.service.LedgerService;
import hanglog.trip.service.TripService;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private final LedgerService ledgerService;

    @PostMapping
    @MemberOnly
    public ResponseEntity<Void> createTrip(
            @Auth final Accessor accessor,
            @RequestBody @Valid final TripCreateRequest tripCreateRequest
    ) {
        final Long tripId = tripService.save(accessor.getMemberId(), tripCreateRequest);
//        final Long tripId = tripService.save(1L, tripCreateRequest);
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

    @PostMapping("/{tripId}")
//    @MemberOnly
    public ResponseEntity<Void> deleteTrip(@Auth final Accessor accessor, @PathVariable final Long tripId) {
        tripService.validateTripByMember(2L, tripId);
        tripService.delete(tripId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{tripId}/expense")
    @MemberOnly
    public ResponseEntity<LedgerResponse> getExpenses(
            @Auth final Accessor accessor,
            @PathVariable final Long tripId
    ) {
        tripService.validateTripByMember(accessor.getMemberId(), tripId);
        final LedgerResponse ledgerResponse = ledgerService.getAllExpenses(tripId);
        return ResponseEntity.ok().body(ledgerResponse);
    }

    @PatchMapping("/{tripId}/share")
    public ResponseEntity<SharedCodeResponse> updateSharedStatus(
            @Auth final Accessor accessor,
            @PathVariable final Long tripId,
            @RequestBody @Valid final SharedStatusRequest sharedStatusRequest
    ) {
        tripService.validateTripByMember(accessor.getMemberId(), tripId);
        final SharedCodeResponse sharedCodeResponse = tripService.updateSharedTripStatus(
                tripId,
                sharedStatusRequest
        );
        return ResponseEntity.ok().body(sharedCodeResponse);
    }

    @PatchMapping("/{tripId}/publish")
    @MemberOnly
    public ResponseEntity<Void> updatePublishedStatus(
            @Auth final Accessor accessor,
            @PathVariable final Long tripId,
            @RequestBody @Valid final PublishedStatusRequest publishedStatusRequest
    ) {
        tripService.validateTripByMember(accessor.getMemberId(), tripId);
        tripService.updatePublishedStatus(tripId, publishedStatusRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/legend")
    public ResponseEntity<Void> createLegendTrip() {
        tripService.save(2L, new TripCreateRequest(
                LocalDate.of(2023, 7, 1),
                LocalDate.of(2023, 8, 30),
                List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L)
        ));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tripId}/legend/items")
    public ResponseEntity<Void> createLegendItems(@PathVariable final Long tripId) {
        tripService.updatePublishedStatus(tripId, new PublishedStatusRequest(true));
        tripService.updateSharedTripStatus(tripId, new SharedStatusRequest(true));
        tripService.createLegendItems(tripId);
        return ResponseEntity.noContent().build();
    }
}
