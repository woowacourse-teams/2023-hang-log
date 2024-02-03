package hanglog.city.service;

import static hanglog.global.exception.ExceptionCode.DUPLICATED_CITY_NAME;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_CITY_ID;

import hanglog.city.domain.City;
import hanglog.city.domain.repository.CityRepository;
import hanglog.city.dto.request.CityRequest;
import hanglog.city.dto.response.CityDetailResponse;
import hanglog.city.dto.response.CityResponse;
import hanglog.global.exception.BadRequestException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CityService {

    private final CityRepository cityRepository;

    @Transactional(readOnly = true)
    public List<CityResponse> getAllCities() {
        final List<City> cities = cityRepository.findAll();
        return cities.stream()
                .map(CityResponse::withCountry)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CityDetailResponse> getAllCitiesDetail() {
        final List<City> cities = cityRepository.findAll();
        return cities.stream()
                .map(CityDetailResponse::of)
                .toList();
    }

    public Long save(final CityRequest cityRequest) {
        validateCityDuplicate(cityRequest);

        return cityRepository.save(City.of(cityRequest)).getId();
    }

    private void validateCityDuplicate(final CityRequest cityRequest) {
        if (cityRepository.existsByNameAndCountry(cityRequest.getName(), cityRequest.getCountry())) {
            throw new BadRequestException(DUPLICATED_CITY_NAME);
        }
    }

    public void update(final Long id, final CityRequest cityRequest) {
        final City city = cityRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_CITY_ID));

        validateCityDuplicate(city, cityRequest);

        city.update(cityRequest);
    }

    private void validateCityDuplicate(final City city, final CityRequest cityRequest) {
        if (!city.isSameNameAndCountry(cityRequest.getName(), cityRequest.getCountry())) {
            validateCityDuplicate(cityRequest);
        }
    }
}
