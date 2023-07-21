package hanglog.trip.presentation;

import hanglog.trip.dto.response.CityResponse;
import hanglog.trip.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<CityResponse>> getCities() {
        final List<CityResponse> cityResponses = cityService.getAllCities();
        return ResponseEntity.ok(cityResponses);
    }
}
