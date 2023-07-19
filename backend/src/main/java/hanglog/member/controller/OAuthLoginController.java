package hanglog.member.controller;

import hanglog.member.service.LoginService;
import hanglog.member.service.Provider;
import hanglog.member.service.dto.UserInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login/oauth2")
public class OAuthLoginController {

    private final Provider provider;

    public OAuthLoginController(final Provider provider) {
        this.provider = provider;
    }

    @GetMapping("/code/{oAuthProvider}")
    public void loginOAuth(@RequestParam final String code, @PathVariable final String oAuthProvider) {
        final LoginService loginService = provider.mappingOAuthProvider(oAuthProvider);
        final String accessCode = loginService.getAccessToken(code);
        final UserInfoDto userInfo = loginService.getUserInfo(accessCode);
        loginService.socialLogin(userInfo.getId(), userInfo.getNickname(), userInfo.getImageUrl());
    }
}
