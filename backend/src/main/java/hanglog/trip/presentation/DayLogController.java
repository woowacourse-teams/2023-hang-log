package hanglog.trip.presentation;


import hanglog.auth.Auth;
import hanglog.trip.dto.request.DayLogUpdateTitleRequest;
import hanglog.trip.dto.request.ItemsOrdinalUpdateRequest;
import hanglog.trip.dto.response.DayLogResponse;
import hanglog.trip.service.DayLogService;
import hanglog.trip.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trips/{tripId}/daylogs/{dayLogId}")
public class DayLogController {

    private final DayLogService dayLogService;
    private final TripService tripService;

    @GetMapping
    public ResponseEntity<DayLogResponse> getDayLog(
            @Auth final Long memberId,
            @PathVariable final Long tripId,
            @PathVariable final Long dayLogId
    ) {
        tripService.validateTripByMember(memberId, tripId);
        final DayLogResponse response = dayLogService.getById(dayLogId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<Void> updateDayLogTitle(
            @Auth final Long memberId,
            @PathVariable final Long tripId,
            @PathVariable final Long dayLogId,
            @RequestBody @Valid final DayLogUpdateTitleRequest request
    ) {
        tripService.validateTripByMember(memberId, tripId);
        dayLogService.updateTitle(dayLogId, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/order")
    public ResponseEntity<Void> updateOrdinalOfItems(
            @Auth final Long memberId,
            @PathVariable final Long tripId,
            @PathVariable final Long dayLogId,
            @RequestBody @Valid final ItemsOrdinalUpdateRequest itemsOrdinalUpdateRequest
    ) {
        tripService.validateTripByMember(memberId, tripId);
        dayLogService.updateOrdinalOfItems(dayLogId, itemsOrdinalUpdateRequest);
        return ResponseEntity.noContent().build();
    }
}
