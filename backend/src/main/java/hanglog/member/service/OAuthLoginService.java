package hanglog.member.service;

import hanglog.member.domain.Member;
import hanglog.member.provider.Provider;
import hanglog.member.provider.ProviderProperties;
import hanglog.member.domain.repository.MemberRepository;
import hanglog.member.dto.AccessTokenResponse;
import hanglog.member.dto.UserInfo;
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
        final ResponseEntity<AccessTokenResponse> accessTokenResponse = restTemplate.exchange(
                properties.getTokenUri(),
                HttpMethod.POST,
                getEntity(code, properties),
                AccessTokenResponse.class
        );
        // TODO : token get 실패시 에러처리
        return accessTokenResponse.getBody().getAccessToken();
    }

    private UserInfo getUserInfo(final String accessToken, final Provider provider) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        final HttpEntity entity = new HttpEntity(headers);
        return (UserInfo) restTemplate.exchange(
                provider.getProperties().getUserUri(),
                HttpMethod.GET,
                entity,
                provider.getResponseDto()
        ).getBody();

    }

    private HttpEntity getEntity(final String code, final ProviderProperties properties) {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", properties.getClientId());
        params.add("client_secret", properties.getClientSecret());
        params.add("redirect_uri", properties.getRedirectUri());
        params.add("grant_type", "authorization_code");

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return new HttpEntity(params, headers);
    }

    // TODO : 토큰 받기
    private Member save(final String socialLoginId, final String nickname, final String image) {
        final Member member = new Member(socialLoginId, nickname, image);
        return memberRepository.findBySocialLoginId(socialLoginId)
                .orElseGet(() -> memberRepository.save(member));
    }
}
