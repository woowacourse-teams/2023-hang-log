package hanglog.share.presentation;

import hanglog.share.dto.request.TripSharedStatusRequest;
import hanglog.share.dto.response.TripSharedCodeResponse;
import hanglog.share.service.ShareService;
import hanglog.trip.dto.response.TripDetailResponse;
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
public class ShareController {

    private final ShareService shareService;

    @GetMapping("/shared-trips/{sharedCode}")
    public ResponseEntity<TripDetailResponse> getSharedTrip(@PathVariable final String sharedCode) {
        final TripDetailResponse tripDetailResponse = shareService.getTripDetail(sharedCode);
        return ResponseEntity.ok().body(tripDetailResponse);
    }

    @PatchMapping("/trips/{tripId}/share")
    public ResponseEntity<TripSharedCodeResponse> updateSharedStatus(
            @PathVariable final Long tripId,
            @RequestBody @Valid final TripSharedStatusRequest tripSharedStatusRequest
    ) {
        final TripSharedCodeResponse tripSharedCodeResponse = shareService.updateSharedStatus(tripId,
                tripSharedStatusRequest);
        return ResponseEntity.ok().body(tripSharedCodeResponse);
    }
}
