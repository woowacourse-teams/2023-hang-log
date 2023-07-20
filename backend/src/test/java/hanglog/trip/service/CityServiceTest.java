package hanglog.trip.service;

import hanglog.trip.domain.City;
import hanglog.trip.domain.repository.CityRepository;
import hanglog.trip.dto.response.CityResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

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
        final City seoul = new City(1L, "서울", "대한민국", new BigDecimal("126.9779692"), new BigDecimal("37.566535"));
        final City busan = new City(2L, "부산", "대한민국", new BigDecimal("129.0756416"), new BigDecimal("35.1795543"));

        given(cityRepository.findAll())
                .willReturn(List.of(seoul, busan));

        // when
        final List<CityResponse> actual = cityService.getAllCities();

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(List.of(CityResponse.of(seoul), CityResponse.of(busan)));
    }
}
