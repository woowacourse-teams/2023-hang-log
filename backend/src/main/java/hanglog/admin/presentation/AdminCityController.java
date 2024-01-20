package hanglog.admin.presentation;


import hanglog.auth.AdminAuth;
import hanglog.auth.AdminOnly;
import hanglog.auth.domain.Accessor;
import hanglog.city.dto.request.CityRequest;
import hanglog.city.dto.response.CityDetailResponse;
import hanglog.city.service.CityService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/cities")
public class AdminCityController {

    private final CityService cityService;

    @GetMapping
    @AdminOnly
    public ResponseEntity<List<CityDetailResponse>> getCitiesDetail(
            @AdminAuth final Accessor accessor
    ) {
        final List<CityDetailResponse> citiesDetail = cityService.getAllCitiesDetail();
        return ResponseEntity.ok(citiesDetail);
    }

    @PostMapping
    @AdminOnly
    public ResponseEntity<Void> createCity(
            @AdminAuth final Accessor accessor,
            @RequestBody @Valid final CityRequest cityRequest
    ) {
        final Long cityId = cityService.save(cityRequest);
        return ResponseEntity.created(URI.create("/admin/cities" + cityId)).build();
    }
}
