package hanglog.trip.presentation;


import hanglog.auth.Auth;
import hanglog.auth.MemberOnly;
import hanglog.auth.domain.Accessor;
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
    @MemberOnly
    public ResponseEntity<DayLogResponse> getDayLog(
            @Auth final Accessor accessor,
            @PathVariable final Long tripId,
            @PathVariable final Long dayLogId
    ) {
        tripService.validateTripByMember(accessor.getMemberId(), tripId);
        final DayLogResponse response = dayLogService.getById(dayLogId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    @MemberOnly
    public ResponseEntity<Void> updateDayLogTitle(
            @Auth final Accessor accessor,
            @PathVariable final Long tripId,
            @PathVariable final Long dayLogId,
            @RequestBody @Valid final DayLogUpdateTitleRequest request
    ) {
        tripService.validateTripByMember(accessor.getMemberId(), tripId);
        dayLogService.updateTitle(dayLogId, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/order")
    @MemberOnly
    public ResponseEntity<Void> updateOrdinalOfItems(
            @Auth final Accessor accessor,
            @PathVariable final Long tripId,
            @PathVariable final Long dayLogId,
            @RequestBody @Valid final ItemsOrdinalUpdateRequest itemsOrdinalUpdateRequest
    ) {
        tripService.validateTripByMember(accessor.getMemberId(), tripId);
        dayLogService.updateOrdinalOfItems(dayLogId, itemsOrdinalUpdateRequest);
        return ResponseEntity.noContent().build();
    }
}
