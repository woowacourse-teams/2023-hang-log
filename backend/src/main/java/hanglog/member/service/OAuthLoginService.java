package hanglog.member.service;

import static hanglog.global.exception.ExceptionCode.INVALID_AUTHORIZATION_CODE;

import hanglog.global.exception.AuthException;
import hanglog.member.domain.Member;
import hanglog.member.domain.repository.MemberRepository;
import hanglog.member.dto.AccessTokenRequest;
import hanglog.member.dto.AccessTokenResponse;
import hanglog.member.dto.UserInfo;
import hanglog.member.provider.Provider;
import hanglog.member.provider.ProviderProperties;
import java.util.Optional;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class OAuthLoginService {

    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;

    public OAuthLoginService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.restTemplate = new RestTemplate();
    }

    public Long login(final Provider provider, final String code) {
        final String accessToken = getAccessToken(code, provider.getProperties());
        final UserInfo userInfo = getUserInfo(accessToken, provider);
        final Member member = save(userInfo.getId(), userInfo.getNickname(), userInfo.getImageUrl());
        return member.getId();
    }

    private String getAccessToken(final String code, final ProviderProperties properties) {
        final AccessTokenRequest accessTokenRequest = AccessTokenRequest.of(code, properties);
        final HttpEntity<AccessTokenRequest> accessTokenRequestEntity = new HttpEntity<>(accessTokenRequest);

        final ResponseEntity<AccessTokenResponse> accessTokenResponse = restTemplate.exchange(
                properties.getTokenUri(),
                HttpMethod.POST,
                accessTokenRequestEntity,
                AccessTokenResponse.class
        );

        return Optional.ofNullable(accessTokenResponse.getBody())
                .orElseThrow(() -> new AuthException(INVALID_AUTHORIZATION_CODE))
                .getAccessToken();
    }

    private UserInfo getUserInfo(final String accessToken, final Provider provider) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        final HttpEntity<MultiValueMap<String, String>> userInfoRequestEntity = new HttpEntity<>(headers);

        return (UserInfo) restTemplate.exchange(
                provider.getProperties().getUserUri(),
                HttpMethod.GET,
                userInfoRequestEntity,
                provider.getResponseDto()
        ).getBody();
    }

    // TODO : 토큰 받기
    private Member save(final String socialLoginId, final String nickname, final String imageUrl) {
        final Member member = new Member(socialLoginId, nickname, imageUrl);
        return memberRepository.findBySocialLoginId(socialLoginId)
                .orElseGet(() -> memberRepository.save(member));
    }
}
