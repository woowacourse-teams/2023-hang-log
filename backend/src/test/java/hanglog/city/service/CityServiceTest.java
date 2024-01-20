package hanglog.city.service;

import static hanglog.trip.fixture.CityFixture.LONDON;
import static hanglog.trip.fixture.CityFixture.PARIS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import hanglog.city.domain.City;
import hanglog.city.domain.repository.CityRepository;
import hanglog.city.dto.request.CityRequest;
import hanglog.city.dto.response.CityDetailResponse;
import hanglog.city.dto.response.CityResponse;
import hanglog.global.exception.InvalidDomainException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class CityServiceTest {

    @InjectMocks
    private CityService cityService;

    @Mock
    private CityRepository cityRepository;

    @DisplayName("모든 도시를 반환한다.")
    @Test
    void getAllCities() {
        // given
        given(cityRepository.findAll())
                .willReturn(List.of(PARIS, LONDON));

        // when
        final List<CityResponse> actual = cityService.getAllCities();

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(CityResponse.withCountry(PARIS), CityResponse.withCountry(LONDON)));
    }

    @DisplayName("모든 도시의 세부 정보를 반환한다.")
    @Test
    void getAllCitiesDetail() {
        // given
        given(cityRepository.findAll())
                .willReturn(List.of(PARIS, LONDON));

        // when
        final List<CityDetailResponse> actual = cityService.getAllCitiesDetail();

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(CityDetailResponse.of(PARIS), CityDetailResponse.of(LONDON)));
    }

    @DisplayName("새로운 도시를 추가한다.")
    @Test
    void save() {
        // given
        final CityRequest cityRequest = new CityRequest(
                PARIS.getName(),
                PARIS.getCountry(),
                PARIS.getLatitude(),
                PARIS.getLongitude()
        );

        given(cityRepository.existsByNameAndCountry(anyString(), anyString())).willReturn(false);
        given(cityRepository.save(any(City.class))).willReturn(PARIS);

        // when
        final Long actualId = cityService.save(cityRequest);

        // then
        assertThat(actualId).isEqualTo(PARIS.getId());
    }

    @DisplayName("도시와 나라가 중복되면 도시를 추가할 수 없다.")
    @Test
    void save_duplicateFail() {
        // given
        final CityRequest cityRequest = new CityRequest(
                PARIS.getName(),
                PARIS.getCountry(),
                PARIS.getLatitude(),
                PARIS.getLongitude()
        );

        given(cityRepository.existsByNameAndCountry(anyString(), anyString())).willReturn(true);

        // when
        assertThatThrownBy(() -> cityService.save(cityRequest))
                .isInstanceOf(InvalidDomainException.class);
    }
}
