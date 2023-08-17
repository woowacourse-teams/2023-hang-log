package hanglog.auth.domain.oauthuserinfo;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class KakaoUserInfo implements OauthUserInfo {

    @JsonProperty("id")
    private String socialLoginId;
    @JsonProperty("properties")
    private Properties properties;

    @Override
    public String getSocialLoginId() {
        return socialLoginId;
    }

    @Override
    public String getNickname() {
        return properties.name;
    }

    @Override
    public String getImageUrl() {
        return properties.image;
    }

    @NoArgsConstructor(access = PRIVATE)
    private static class Properties {

        @JsonProperty("nickname")
        private String name;
        @JsonProperty("profile_image")
        private String image;
    }
}
