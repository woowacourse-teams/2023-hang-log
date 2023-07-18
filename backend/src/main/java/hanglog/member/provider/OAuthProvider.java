package hanglog.member.provider;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

@Getter
public abstract class OAuthProvider {

    private static final String GOOGLE = "google";
    private static final String KAKAO = "kakao";

    protected final String socialId;
    protected final String nickname;
    protected final String image;

    protected OAuthProvider(final JsonNode userResourceNode) {
        this.socialId = setSocialLoginId(userResourceNode);
        this.nickname = setNickname(userResourceNode);
        this.image = setPicture(userResourceNode);
    }

    public static OAuthProvider mappingProvider(final JsonNode userResourceNode, final String registrationId) {
        if (registrationId.equals(GOOGLE)) {
            return new GoogleOAuthProvider(userResourceNode);
        }
        if (registrationId.equals(KAKAO)) {
            return new KakaoOAuthProvider(userResourceNode);
        }
        throw new IllegalArgumentException();
    }

    abstract protected String setSocialLoginId(final JsonNode userResourceNode);

    abstract protected String setNickname(final JsonNode userResourceNode);

    abstract protected String setPicture(final JsonNode userResourceNode);
}
