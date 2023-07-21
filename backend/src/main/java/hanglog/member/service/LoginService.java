package hanglog.member.service;

import hanglog.member.Member;
import hanglog.member.repository.MemberRepository;
import hanglog.member.service.dto.AccessTokenResponseDto;
import hanglog.member.service.dto.UserInfoDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public abstract class LoginService {

    protected final RestTemplate restTemplate;
    protected final String clientId;
    protected final String clientSecret;
    protected final String redirectUri;
    protected final String tokenUri;
    protected final String userUri;
    private final MemberRepository memberRepository;

    public LoginService(
            final MemberRepository memberRepository,
            final String clientId,
            final String clientSecret,
            final String redirectUri,
            final String tokenUri,
            final String userUri
    ) {
        this.memberRepository = memberRepository;
        this.restTemplate = new RestTemplate();
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.tokenUri = tokenUri;
        this.userUri = userUri;
    }

    public String getAccessToken(final String code) {
        final ResponseEntity<AccessTokenResponseDto> accessTokenResponse = restTemplate.exchange(
                tokenUri,
                HttpMethod.POST,
                getEntity(code),
                AccessTokenResponseDto.class
        );
        // TODO : token get 실패시 에러처리
        return accessTokenResponse.getBody().getAccessToken();
    }

    abstract public UserInfoDto getUserInfo(final String accessToken);

    // TODO : 토큰 발기
    @Transactional
    public Member socialLogin(final String socialLoginId, final String nickname, final String image) {
        return memberRepository.findBySocialLoginId(socialLoginId)
                .orElseGet(() -> saveMember(socialLoginId, nickname, image));
    }

    private HttpEntity getEntity(final String code) {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
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
