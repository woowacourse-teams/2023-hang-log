package hanglog.member.domain.auth.google;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanglog.member.domain.auth.UserInfo;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class GoogleUserInfo implements UserInfo {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("picture")
    private String picture;

    @Override
    public String getId() {
        return id;
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
