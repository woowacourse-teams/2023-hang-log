package hanglog.trip.service;

import static hanglog.expense.fixture.CityFixture.LONDON;
import static hanglog.expense.fixture.CityFixture.PARIS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import hanglog.city.dto.response.CityResponse;
import hanglog.trip.domain.repository.CityRepository;
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
}
