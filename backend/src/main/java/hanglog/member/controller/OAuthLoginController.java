package hanglog.member.controller;

import hanglog.member.provider.Provider;
import hanglog.member.service.OAuthLoginService;
import hanglog.member.service.dto.UserInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login/oauth2")
public class OAuthLoginController {

    private final OAuthLoginService oAuthLoginService;

    public OAuthLoginController(final OAuthLoginService oAuthLoginService) {
        this.oAuthLoginService = oAuthLoginService;
    }

    @GetMapping("/code/{oAuthProvider}")
    public void googleLoginOAuth(@RequestParam final String code, @PathVariable final String oAuthProvider) {
        final Provider provider = Provider.mappingProvider(oAuthProvider);
        final String accessCode = oAuthLoginService.getAccessToken(code, provider.getProperties());
        final UserInfoDto userInfo = oAuthLoginService.getUserInfo(accessCode, provider);
        oAuthLoginService.socialLogin(userInfo.getId(), userInfo.getNickname(), userInfo.getImageUrl());
    }
}
