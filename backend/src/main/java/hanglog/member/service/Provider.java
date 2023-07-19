package hanglog.member.service;

import org.springframework.stereotype.Component;

@Component
public class Provider {

    private final LoginService googleLoginService;
    private final LoginService kakaoLoginService;

    public Provider(final LoginService googleLoginService, final LoginService kakaoLoginService) {
        this.googleLoginService = googleLoginService;
        this.kakaoLoginService = kakaoLoginService;
    }

    public LoginService mappingOAuthProvider(final String oAuthProvider) {
        if (oAuthProvider.equals("google")) {
            return googleLoginService;
        }
        if (oAuthProvider.equals("kakao")) {
            return kakaoLoginService;
        }
        throw new IllegalArgumentException();
    }
}
