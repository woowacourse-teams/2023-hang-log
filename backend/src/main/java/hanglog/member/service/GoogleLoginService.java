package hanglog.member.service;

import hanglog.member.repository.MemberRepository;
import hanglog.member.service.dto.GoogleUserInfoResponseDto;
import hanglog.member.service.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class GoogleLoginService extends LoginService {

    private static final String PROPERTIES_PATH = "${spring.oauth2.provider.google.";

    private GoogleLoginService(
            final MemberRepository memberRepository,
            @Value(PROPERTIES_PATH + "client-id}") final String clientId,
            @Value(PROPERTIES_PATH + "client-secret}") final String clientSecret,
            @Value(PROPERTIES_PATH + "redirect-uri}") final String redirectUri,
            @Value(PROPERTIES_PATH + "token-uri}") final String tokenUri,
            @Value(PROPERTIES_PATH + "user-info}") final String userUri
    ) {
        super(memberRepository, clientId, clientSecret, redirectUri, tokenUri, userUri);
    }

    @Override
    public UserInfoDto getUserInfo(final String accessToken) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        final HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(userUri, HttpMethod.GET, entity, GoogleUserInfoResponseDto.class).getBody();
    }
}
