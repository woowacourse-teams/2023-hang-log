package hanglog.global;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(value = {
        "classpath:data/truncate.sql",
        "classpath:data/currency.sql",
        "classpath:data/cities.sql",
        "classpath:data/categories.sql"
})
public abstract class IntegrationTest {

    @LocalServerPort
    int port;

    public static String parseUri(final String uri) {
        final String[] parts = uri.split("/");
        return parts[parts.length - 1];
    }

    @BeforeEach
    void setPort() {
        RestAssured.port = port;
    }
}
