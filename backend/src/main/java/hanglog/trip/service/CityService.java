package hanglog.trip.service;

import hanglog.trip.domain.City;
import hanglog.trip.domain.repository.CityRepository;
import hanglog.trip.dto.response.CityResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(final CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<CityResponse> getAllCities() {
        List<City> cities = cityRepository.findAll();
        return cities.stream()
                .map(CityResponse::of)
                .toList();
    }
}
