package hanglog.member.dto;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanglog.member.provider.ProviderProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class AccessTokenRequest {

    private static final String GRANT_TYPE = "authorization_code";

    private final String code;
    @JsonProperty("client_id")
    private final String clientId;

    @JsonProperty("client_secret")
    private final String clientSecret;

    @JsonProperty("redirect_uri")
    private final String redirectUri;

    @JsonProperty("grant_type")
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
