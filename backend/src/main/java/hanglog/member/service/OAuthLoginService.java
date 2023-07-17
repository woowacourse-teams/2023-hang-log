package hanglog.member.service;

import com.fasterxml.jackson.databind.JsonNode;
import hanglog.member.Member;
import hanglog.member.exception.AlreadyExistUserException;
import hanglog.member.mapper.OAuthProvider;
import hanglog.member.repository.MemberRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
            final MemberRepository memberRepository,
            final RestTemplate restTemplate
    ) {
        this.env = env;
        this.memberRepository = memberRepository;
        this.restTemplate = restTemplate;
    }

    public Member socialSignUp(final String code, final String registrationId) {
        final String accessToken = getAccessToken(code, registrationId);
        final JsonNode userResourceNode = getUserResource(accessToken, registrationId);

        final OAuthProvider oAuthProvider = OAuthProvider.mappingProvider(userResourceNode, registrationId);
        if (memberRepository.existsBySocialLoginId(userResourceNode.get("id").asText())) {
            throw new AlreadyExistUserException("member already exist");
        }

        final Member member = new Member(oAuthProvider.getSocialLoginId(), oAuthProvider.getNickname(),
                oAuthProvider.getPicture());
        return memberRepository.save(member);
    }

    private String getAccessToken(final String authorizationCode, final String registrationId) {
        final String clientId = env.getProperty(PROPERTY_PATH + registrationId + ".client-id");
        final String clientSecret = env.getProperty(PROPERTY_PATH + registrationId + ".client-secret");
        final String redirectUri = env.getProperty(PROPERTY_PATH + registrationId + ".redirect-uri");
        final String tokenUri = env.getProperty(PROPERTY_PATH + registrationId + ".token-uri");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final HttpEntity entity = new HttpEntity(params, headers);

        final ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
        final JsonNode accessTokenNode = responseNode.getBody();
        return accessTokenNode.get("access_token").asText();
    }

    private JsonNode getUserResource(final String accessToken, final String registrationId) {
        final String resourceUri = env.getProperty(PROPERTY_PATH + registrationId + ".user-info");

        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        final HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
    }
}
