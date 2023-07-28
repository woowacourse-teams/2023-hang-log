package hanglog.member.dto;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class KakaoUserInfoResponseDto implements UserInfoDto {

    @JsonProperty("id")
    private String id;
    @JsonProperty("properties")
    private Properties properties;

    @Override
    public String getId() {
        return id;
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
