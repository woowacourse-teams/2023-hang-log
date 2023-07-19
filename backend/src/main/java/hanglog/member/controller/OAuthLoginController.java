package hanglog.member.controller;

import com.fasterxml.jackson.databind.JsonNode;
import hanglog.member.service.OAuthLoginService;
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
    public void loginOAuth(@RequestParam final String code, @PathVariable final String oAuthProvider) {
        final String accessCode = oAuthLoginService.getAccessToken(code, oAuthProvider);
        final JsonNode userInfo = oAuthLoginService.getUserInfo(accessCode, oAuthProvider);
        oAuthLoginService.socialLogin(userInfo, oAuthProvider);
    }
}
