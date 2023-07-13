package hanglog.member.service;

import static hanglog.feature.MemberFeature.GOOGLE_USER_INFO_JSON_STRING;
import static hanglog.feature.MemberFeature.KAKAO_USER_INFO_JSON_STRING;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.member.Member;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class OAuthLoginServiceTest {
    @Autowired
    private Environment env;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private OAuthLoginService oAuthLoginService;

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"google","kakao"})
    void socialSignUp( final String registrationId ) throws JsonProcessingException {
        // given
        String resourceUri = env.getProperty("spring.oauth2.client.registration." + registrationId + ".user-info");
        String tokenUri = env.getProperty("spring.oauth2.client.registration." + registrationId + ".token-uri");

        Mockito
                .when(restTemplate.exchange(tokenUri, HttpMethod.POST,makeEntity(registrationId), JsonNode.class))
                .thenReturn(ResponseEntity.of(Optional.of(new ObjectMapper().readTree(GOOGLE_USER_INFO_JSON_STRING))));
        Mockito
                .when(restTemplate.exchange(resourceUri, HttpMethod.GET,makeEntity(registrationId), JsonNode.class))
                .thenReturn(ResponseEntity.of(Optional.of(new ObjectMapper().readTree(GOOGLE_USER_INFO_JSON_STRING))));

        // when
        Member actual = oAuthLoginService.socialSignUp("code",registrationId);

        //then
        assertSoftly(softly->{
            softly.assertThat(actual.getId()).isEqualTo(123l);
            softly.assertThat(actual.getName()).isEqualTo(registrationId+"_test");
            softly.assertThat(actual.getImage()).isEqualTo(registrationId+"_image_url");
        });
    }

    private HttpEntity makeEntity(final String registrationId){
        String clientId = env.getProperty("spring.oauth2.client.registration." + registrationId + ".client-id");
        String clientSecret = env.getProperty("spring.oauth2.client.registration." + registrationId + ".client-secret");
        String redirectUri = env.getProperty("spring.oauth2.client.registration." + registrationId + ".redirect-uri");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", "code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return new HttpEntity(params, headers);
    }
}
