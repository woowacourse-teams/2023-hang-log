package hanglog.community.presentation;

import static org.springframework.data.domain.Sort.Direction.DESC;

import hanglog.auth.Auth;
import hanglog.auth.domain.Accessor;
import hanglog.community.dto.response.CommunityTripListResponse;
import hanglog.community.dto.response.RecommendTripListResponse;
import hanglog.community.service.CommunityService;
import hanglog.trip.dto.response.TripDetailResponse;
import hanglog.trip.dto.response.LedgerResponse;
import hanglog.trip.service.LedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;
    private final LedgerService ledgerService;

    @GetMapping("/trips")
    public ResponseEntity<CommunityTripListResponse> getTrips(
            @Auth final Accessor accessor,
            @PageableDefault(sort = "publishedTrip.id", direction = DESC) final Pageable pageable
    ) {
        final CommunityTripListResponse communityTripListResponse = communityService.getCommunityTripsByPage(accessor, pageable);
        return ResponseEntity.ok().body(communityTripListResponse);
    }

    @GetMapping("/recommends")
    public ResponseEntity<RecommendTripListResponse> getRecommendTrips(@Auth final Accessor accessor) {
        final RecommendTripListResponse tripResponses = communityService.getRecommendTrips(accessor);
        return ResponseEntity.ok().body(tripResponses);
    }

    @GetMapping("/trips/{tripId}")
    public ResponseEntity<TripDetailResponse> getTrip(
            @Auth final Accessor accessor,
            @PathVariable final Long tripId
    ) {
        final TripDetailResponse tripDetailResponse = communityService.getTripDetail(
                accessor,
                tripId
        );
        return ResponseEntity.ok().body(tripDetailResponse);
    }

    @GetMapping("/trips/{tripId}/expense")
    public ResponseEntity<LedgerResponse> getExpenses(@PathVariable final Long tripId) {
        final LedgerResponse ledgerResponse = ledgerService.getAllExpenses(tripId);
        return ResponseEntity.ok().body(ledgerResponse);
    }
}
