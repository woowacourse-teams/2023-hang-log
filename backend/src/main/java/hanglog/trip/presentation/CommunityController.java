package hanglog.trip.presentation;

import static org.springframework.data.domain.Sort.Direction.DESC;

import hanglog.auth.Auth;
import hanglog.auth.domain.Accessor;
import hanglog.trip.dto.response.CommunityTripResponse;
import hanglog.trip.service.CommunityService;
import java.util.List;
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
    ResponseEntity<List<CommunityTripResponse>> getTrips(
            @Auth Accessor accessor,
            @PageableDefault(sort = "publishedTrip.id", direction = DESC) Pageable pageable
    ) {
        final List<CommunityTripResponse> tripResponses = communityService.getTripsByPage(accessor, pageable);
        return ResponseEntity.ok().body(tripResponses);
    }
}
