package hanglog.login.infrastructure.oauthuserinfo;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanglog.login.domain.OauthUserInfo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class KakaoUserInfo implements OauthUserInfo {

    @JsonProperty("id")
    private String socialLoginId;
    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Override
    public String getSocialLoginId() {
        return socialLoginId;
    }

    @Override
    public String getNickname() {
        return kakaoAccount.nickname;
    }

    @Override
    public String getImageUrl() {
        return kakaoAccount.image;
    }

    @NoArgsConstructor(access = PRIVATE)
    private static class KakaoAccount {

        @JsonProperty("profile.nickname")
        private String nickname;
        @JsonProperty("profile.profile_image_url")
        private String image;
    }
}
