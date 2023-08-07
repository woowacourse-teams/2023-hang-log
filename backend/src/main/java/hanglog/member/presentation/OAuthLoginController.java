package hanglog.member.presentation;

import hanglog.member.service.OAuthLoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login/oauth2")
public class OAuthLoginController {

    private final OAuthLoginService oAuthLoginService;

    public OAuthLoginController(final OAuthLoginService oAuthLoginService) {
        this.oAuthLoginService = oAuthLoginService;
    }

    @GetMapping("/code/{provider}")
    public ResponseEntity<Void> loginOAuth(@PathVariable final String provider, @RequestParam final String code) {
        final Long memberId = oAuthLoginService.login(provider, code);
        return ResponseEntity.ok().build();
    }
}
