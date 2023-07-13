package hanglog.member.mapper;

import com.fasterxml.jackson.databind.JsonNode;

public class GoogleOAuthProvider extends OAuthProvider {

    public GoogleOAuthProvider(final JsonNode userResourceNode) {
        super(userResourceNode);
    }

    @Override
    public Long getId() {
        return userResourceNode.get("id").asLong();
    }

    @Override
    public String getNickname() {
        return userResourceNode.get("name").asText();
    }

    @Override
    public String getPicture() {
        return userResourceNode.get("picture").asText();
    }
}
