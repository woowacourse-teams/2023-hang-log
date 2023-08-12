package hanglog;

import static hanglog.trip.fixture.IntegrationFixture.CULTURE_CATEGORY;
import static hanglog.trip.fixture.IntegrationFixture.EDINBURGH;
import static hanglog.trip.fixture.IntegrationFixture.FOOD_CATEGORY;
import static hanglog.trip.fixture.IntegrationFixture.LONDON;
import static hanglog.trip.fixture.IntegrationFixture.PARIS;
import static hanglog.trip.fixture.IntegrationFixture.TOKYO;

import hanglog.category.domain.repository.CategoryRepository;
import hanglog.trip.domain.repository.CityRepository;
import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CityRepository cityRepository;

    @BeforeEach
    void setPort() {
        RestAssured.port = port;

        categoryRepository.saveAll(List.of(FOOD_CATEGORY, CULTURE_CATEGORY));
        cityRepository.saveAll(List.of(LONDON, EDINBURGH, PARIS, TOKYO));
    }

    public static String parseUri(String uri) {
        String[] parts = uri.split("/");
        return parts[parts.length - 1];
    }
}
