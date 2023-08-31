package hanglog.trip.service;

import hanglog.city.dto.response.CityResponse;
import hanglog.trip.domain.City;
import hanglog.trip.domain.repository.CityRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CityService {

    private final CityRepository cityRepository;

    public List<CityResponse> getAllCities() {
        final List<City> cities = cityRepository.findAll();
        return cities.stream()
                .map(CityResponse::withCountry)
                .toList();
    }
}
