package hanglog.member.provider;

import com.fasterxml.jackson.databind.JsonNode;

public class GoogleOAuthProvider extends OAuthProvider {

    public GoogleOAuthProvider(final JsonNode userResourceNode) {
        super(userResourceNode);
    }

    @Override
    protected String setSocialLoginId(final JsonNode userResourceNode) {
        return userResourceNode.get("id").asText();
    }

    @Override
    protected String setNickname(final JsonNode userResourceNode) {
        return userResourceNode.get("name").asText();
    }

    @Override
    protected String setPicture(final JsonNode userResourceNode) {
        return userResourceNode.get("picture").asText();
    }
}
