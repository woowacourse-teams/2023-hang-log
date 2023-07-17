package hanglog.member.mapper;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class OAuthProvider {

    protected final JsonNode userResourceNode;

    protected OAuthProvider(final JsonNode userResourceNode) {
        this.userResourceNode = userResourceNode;
    }

    // todo : custom exception
    public static OAuthProvider mappingProvider(final JsonNode userResourceNode, final String registrationId) {
        if (registrationId.equals("google")) {
            return new GoogleOAuthProvider(userResourceNode);
        }
        if (registrationId.equals("kakao")) {
            return new KakaoOAuthProvider(userResourceNode);
        }
        throw new IllegalArgumentException();
    }

    abstract public String getSocialLoginId();

    abstract public String getNickname();

    abstract public String getPicture();
}
