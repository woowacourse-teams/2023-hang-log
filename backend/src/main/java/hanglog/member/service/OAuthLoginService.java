package hanglog.member.service;

import com.fasterxml.jackson.databind.JsonNode;
import hanglog.member.Member;
import hanglog.member.provider.OAuthProvider;
import hanglog.member.repository.MemberRepository;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class OAuthLoginService {

    private static final String PROPERTY_PATH = "spring.oauth2.client.registration.";
    private final Environment env;
    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;

    public OAuthLoginService(
            final Environment env,
            final MemberRepository memberRepository
    ) {
        this.env = env;
        this.memberRepository = memberRepository;
        this.restTemplate = new RestTemplate();
    }

    public String getAccessToken(final String authorizationCode, final String registrationId) {
        final ResponseEntity<JsonNode> responseNode = restTemplate.exchange(
                env.getProperty(PROPERTY_PATH + registrationId + ".token-uri"),
                HttpMethod.POST,
                getEntity(authorizationCode, registrationId),
                JsonNode.class
        );
        final JsonNode accessTokenNode = responseNode.getBody();
        return accessTokenNode.get("access_token").asText();
    }

    public JsonNode getUserInfo(final String accessToken, final String registrationId) {
        final String resourceUri = env.getProperty(PROPERTY_PATH + registrationId + ".user-info");

        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        final HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
    }

    public Member socialLogin(final JsonNode userResourceNode, final String registrationId) {
        final OAuthProvider oAuthProvider = OAuthProvider.mappingProvider(userResourceNode, registrationId);
        String socialLoginId = userResourceNode.get("id").asText();

        return memberRepository.findBySocialLoginId(socialLoginId).orElseGet(() -> saveMember(oAuthProvider));

    }

    private HttpEntity getEntity(final String authorizationCode, final String registrationId) {
        final String clientId = env.getProperty(PROPERTY_PATH + registrationId + ".client-id");
        final String clientSecret = env.getProperty(PROPERTY_PATH + registrationId + ".client-secret");
        final String redirectUri = env.getProperty(PROPERTY_PATH + registrationId + ".redirect-uri");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return new HttpEntity(params, headers);
    }

    private Member saveMember(OAuthProvider oAuthProvider) {
        return memberRepository.save(
                new Member(
                        oAuthProvider.getSocialId(),
                        oAuthProvider.getNickname(),
                        oAuthProvider.getImage()
                )
        );
    }
}
