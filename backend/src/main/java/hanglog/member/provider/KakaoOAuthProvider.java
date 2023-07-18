package hanglog.member.provider;

import com.fasterxml.jackson.databind.JsonNode;

public class KakaoOAuthProvider extends OAuthProvider {

    protected KakaoOAuthProvider(final JsonNode userResourceNode) {
        super(userResourceNode);
    }

    @Override
    protected String setSocialLoginId(final JsonNode userResourceNode) {
        return userResourceNode.get("id").asText();
    }

    @Override
    protected String setNickname(final JsonNode userResourceNode) {
        return userResourceNode.get("properties").get("nickname").asText();
    }

    @Override
    protected String setPicture(final JsonNode userResourceNode) {
        return userResourceNode.get("properties").get("profile_image").asText();
    }
}
