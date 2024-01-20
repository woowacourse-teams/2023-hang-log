package hanglog.city.service;

import hanglog.city.domain.City;
import hanglog.city.domain.repository.CityRepository;
import hanglog.city.dto.request.CityRequest;
import hanglog.city.dto.response.CityDetailResponse;
import hanglog.city.dto.response.CityResponse;
import hanglog.global.exception.ExceptionCode;
import hanglog.global.exception.InvalidDomainException;
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
        if (cityRepository.existsByNameAndCountry(cityRequest.getName(), cityRequest.getCountry())) {
            throw new InvalidDomainException(ExceptionCode.DUPLICATED_CITY_NAME);
        }

        return cityRepository.save(City.of(cityRequest)).getId();
    }

}
