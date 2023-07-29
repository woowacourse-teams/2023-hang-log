package hanglog.member.dto;

import static lombok.AccessLevel.PRIVATE;

import hanglog.member.provider.ProviderProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class AccessTokenRequest {

    private static final String GRANT_TYPE = "authorization_code";

    private final String code;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String grantType;

    public static AccessTokenRequest of(final String code, final ProviderProperties properties) {
        return new AccessTokenRequest(
                code,
                properties.getClientId(),
                properties.getClientSecret(),
                properties.getRedirectUri(),
                GRANT_TYPE
        );
    }
}
