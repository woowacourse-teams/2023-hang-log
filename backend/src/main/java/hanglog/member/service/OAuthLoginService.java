package hanglog.member.service;

import hanglog.member.Member;
import hanglog.member.provider.Provider;
import hanglog.member.provider.ProviderProperties;
import hanglog.member.repository.MemberRepository;
import hanglog.member.service.dto.AccessTokenResponseDto;
import hanglog.member.service.dto.UserInfoDto;
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
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;

    public OAuthLoginService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.restTemplate = new RestTemplate();
    }

    public String getAccessToken(final String code, final ProviderProperties properties) {
        System.out.println(properties.getTokenUri());
        final ResponseEntity<AccessTokenResponseDto> accessTokenResponse = restTemplate.exchange(
                properties.getTokenUri(),
                HttpMethod.POST,
                getEntity(code, properties),
                AccessTokenResponseDto.class
        );
        // TODO : token get 실패시 에러처리
        return accessTokenResponse.getBody().getAccessToken();
    }

    public UserInfoDto getUserInfo(final String accessToken, final Provider provider) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        final HttpEntity entity = new HttpEntity(headers);
        return (UserInfoDto) restTemplate.exchange(
                provider.getProperties().getUserUri(),
                HttpMethod.GET,
                entity,
                provider.getResponseDto()
        ).getBody();

    }

    // TODO : 토큰 받기
    @Transactional
    public Member socialLogin(final String socialLoginId, final String nickname, final String image) {
        return memberRepository.findBySocialLoginId(socialLoginId)
                .orElseGet(() -> saveMember(socialLoginId, nickname, image));
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

    private Member saveMember(final String socialLoginId, final String nickname, final String image) {
        final Member member = new Member(socialLoginId, nickname, image);
        return memberRepository.save(member);
    }
}
