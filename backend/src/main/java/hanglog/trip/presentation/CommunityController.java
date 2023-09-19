package hanglog.trip.presentation;

import static org.springframework.data.domain.Sort.Direction.DESC;

import hanglog.auth.Auth;
import hanglog.auth.domain.Accessor;
import hanglog.trip.dto.response.CommunityTripsResponse;
import hanglog.trip.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/trips")
    ResponseEntity<CommunityTripsResponse> getTrips(
            @Auth Accessor accessor,
            @PageableDefault(sort = "publishedTrip.id", direction = DESC) Pageable pageable
    ) {
        final CommunityTripsResponse tripResponses = communityService.getTripsByPage(accessor, pageable);
        return ResponseEntity.ok().body(tripResponses);
    }
}
