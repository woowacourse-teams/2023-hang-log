package hanglog.member.service;

import static hanglog.member.mapper.fixture.MemberFixture.KAKAO_USER_INFO_JSON_STRING;
import static org.assertj.core.api.Assertions.assertThat;
import static hanglog.member.mapper.fixture.MemberFixture.GOOGLE_USER_INFO_JSON_STRING;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import hanglog.member.Member;
import hanglog.member.repository.MemberRepository;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class OAuthLoginServiceTest {

    private static final String PROPERTY_PATH = "spring.oauth2.client.registration.";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment environment;

    @Autowired
    private OAuthLoginService oAuthLoginService;

    private MockRestServiceServer mockRestServiceServer;

    @BeforeEach
    void SetUp() throws NoSuchFieldException, IllegalAccessException {
        this.mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }
    @ParameterizedTest(name = "{0}")
    @MethodSource("registrationIdProvider")
    void socialSignUp(final String registrationId, final String jsonString) {

        // given
        final String tokenUri = environment.getProperty(PROPERTY_PATH + registrationId + ".token-uri");
        final String infoUri = environment.getProperty(PROPERTY_PATH + registrationId + ".user-info");
        final String accessToken = "{\"access_token\":\"access_token\"}";

        mockRestServiceServer.expect(requestTo(tokenUri))
                .andRespond(withSuccess(accessToken, APPLICATION_JSON));
        mockRestServiceServer.expect(requestTo(infoUri))
                .andRespond(withSuccess(jsonString, APPLICATION_JSON));

        // when
        Member actual = oAuthLoginService.socialSignUp("code",registrationId);

        // then
        assertThat(actual.getSocialLoginId()).isEqualTo(registrationId+"_id");
    }

    private static Stream<Arguments> registrationIdProvider(){
        return Stream.of(
                Arguments.of("google",GOOGLE_USER_INFO_JSON_STRING),
                Arguments.of("kakao",KAKAO_USER_INFO_JSON_STRING)
        );
    }
}
