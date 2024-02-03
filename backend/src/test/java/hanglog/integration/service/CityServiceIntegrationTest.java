package hanglog.integration.service;


import static hanglog.integration.IntegrationFixture.LONDON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import hanglog.city.dto.request.CityRequest;
import hanglog.city.service.CityService;
import hanglog.global.exception.BadRequestException;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql(value = {
        "classpath:data/truncate.sql",
        "classpath:data/cities.sql"
})
@Import(CityService.class)
public class CityServiceIntegrationTest {

    @Autowired
    private CityService cityService;

    private static final CityRequest CITY_REQUEST = new CityRequest(
            "name",
            "country",
            BigDecimal.valueOf(123.12345),
            BigDecimal.valueOf(123.12345)
    );

    @DisplayName("카테고리 목록을 조회한다.")
    @Test
    void getAllCitiesDetail() {
        // when & then
        assertThat(cityService.getAllCitiesDetail()).isNotEmpty();
    }

    @DisplayName("새로운 카테고리를 저장한다.")
    @Test
    void save() {
        // when & then
        assertDoesNotThrow(() -> cityService.save(CITY_REQUEST));
    }

    @DisplayName("카테고리 이름을 수정한다.")
    @Test
    void update_CityName() {
        // given
        final Long id = cityService.save(CITY_REQUEST);
        final CityRequest updateRequest = new CityRequest(
                "newName",
                "newCountry",
                CITY_REQUEST.getLatitude(),
                CITY_REQUEST.getLongitude()
        );

        // when & then
        assertDoesNotThrow(() -> cityService.update(id, updateRequest));
    }

    @DisplayName("이미 존재하는 카테고리 이름으로 수정할 수 없다.")
    @Test
    void update_CityNameFail() {
        // given
        final Long id = cityService.save(CITY_REQUEST);
        final CityRequest updateRequest = new CityRequest(
                LONDON.getName(),
                LONDON.getCountry(),
                CITY_REQUEST.getLatitude(),
                CITY_REQUEST.getLongitude()
        );

        // when & then
        assertThatThrownBy(() -> cityService.update(id, updateRequest))
                .isInstanceOf(BadRequestException.class);
    }
}
