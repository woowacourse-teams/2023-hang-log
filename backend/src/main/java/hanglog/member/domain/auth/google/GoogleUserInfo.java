package hanglog.member.domain.auth.google;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanglog.member.domain.auth.UserInfo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class GoogleUserInfo implements UserInfo {

    @JsonProperty("id")
    private String socialLoginId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("picture")
    private String picture;

    public String getSocialLoginId() {
        return socialLoginId;
    }

    @Override
    public String getNickname() {
        return name;
    }

    @Override
    public String getImageUrl() {
        return picture;
    }
}
