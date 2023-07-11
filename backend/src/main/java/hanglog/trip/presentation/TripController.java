package hanglog.trip.presentation;

import hanglog.trip.presentation.dto.request.TripRequest;
import hanglog.trip.service.TripService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    @PostMapping
    public ResponseEntity<Void> createTrip(@RequestBody @Valid final TripRequest tripRequest) {
        final Long id = tripService.save(tripRequest);
        return ResponseEntity.created(URI.create("/trips/" + id)).build();
    }

}
