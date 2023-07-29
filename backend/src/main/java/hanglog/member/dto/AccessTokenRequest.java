package hanglog.member.dto;

import hanglog.member.provider.ProviderProperties;
import lombok.Getter;

@Getter
public class AccessTokenRequest {

    private final String code;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String grantType;

    public AccessTokenRequest(final String code, final ProviderProperties properties) {
        this.code = code;
        this.clientId = properties.getClientId();
        this.clientSecret = properties.getClientSecret();
        this.redirectUri = properties.getRedirectUri();
        this.grantType = "authorization_code";
    }
}
