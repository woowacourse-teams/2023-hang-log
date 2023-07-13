package hanglog.member.mapper;

import com.fasterxml.jackson.databind.JsonNode;

public class KakaoOAuthProvider extends OAuthProvider {

    protected KakaoOAuthProvider(final JsonNode userResourceNode) {
        super(userResourceNode);
    }

    @Override
    public Long getId() {
        return userResourceNode.get("id").asLong();
    }

    @Override
    public String getNickname() {
        return userResourceNode.get("properties").get("nickname").asText();
    }

    @Override
    public String getPicture() {
        return userResourceNode.get("properties").get("profile_image").asText();
    }
}
