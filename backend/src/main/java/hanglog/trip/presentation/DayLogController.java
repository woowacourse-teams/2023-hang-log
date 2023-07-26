package hanglog.trip.presentation;


import hanglog.trip.dto.request.DayLogUpdateTitleRequest;
import hanglog.trip.dto.request.ItemsOrdinalUpdateRequest;
import hanglog.trip.dto.response.DayLogGetResponse;
import hanglog.trip.service.DayLogService;
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

    @GetMapping
    public ResponseEntity<DayLogGetResponse> getDayLog(
            @PathVariable final Long tripId,
            @PathVariable final Long dayLogId) {
        final DayLogGetResponse response = dayLogService.getById(dayLogId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<Void> updateDayLogTitle(
            @PathVariable final Long tripId,
            @PathVariable final Long dayLogId,
            @RequestBody @Valid final DayLogUpdateTitleRequest request) {
        dayLogService.updateTitle(dayLogId, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/order")
    public ResponseEntity<Void> updateOrdinalOfItems(
            @PathVariable final Long tripId,
            @PathVariable final Long dayLogId,
            @RequestBody @Valid final ItemsOrdinalUpdateRequest itemsOrdinalUpdateRequest) {
        dayLogService.updateOrdinalOfItems(dayLogId, itemsOrdinalUpdateRequest);
        return ResponseEntity.noContent().build();
    }
}
