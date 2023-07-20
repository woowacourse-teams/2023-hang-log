package hanglog.member.controller;

import hanglog.member.Member;
import hanglog.member.service.LoginService;
import hanglog.member.service.dto.UserInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login/oauth2")
public class OAuthLoginController {

    private final LoginService googleLoginService;
    private final LoginService kakaoLoginService;

    public OAuthLoginController(final LoginService googleLoginService, final LoginService kakaoLoginService) {
        this.googleLoginService = googleLoginService;
        this.kakaoLoginService = kakaoLoginService;
    }

    @GetMapping("/code/google")
    public void googleLoginOAuth(@RequestParam final String code) {
        loginOAuth(googleLoginService, code);
    }

    @GetMapping("/code/kakao")
    public void kakaoLoginOAuth(@RequestParam final String code) {
        loginOAuth(kakaoLoginService, code);
    }

    private Member loginOAuth(final LoginService loginService, final String code) {
        final String accessCode = loginService.getAccessToken(code);
        final UserInfoDto userInfo = loginService.getUserInfo(accessCode);
        return loginService.socialLogin(userInfo.getId(), userInfo.getNickname(), userInfo.getImageUrl());
    }
}
