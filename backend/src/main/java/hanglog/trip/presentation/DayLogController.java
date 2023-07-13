package hanglog.trip.presentation;


import hanglog.trip.presentation.dto.response.DayLogGetResponse;
import hanglog.trip.service.DayLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trips/{tripId}/daylog/{id}")
public class DayLogController {

    private final DayLogService dayLogService;

    @GetMapping
    public ResponseEntity<DayLogGetResponse> getDayLog(@PathVariable final Long tripId, @PathVariable final Long id) {
        final DayLogGetResponse response = dayLogService.getDayLogById(id);
        return ResponseEntity.ok(response);
    }
}


