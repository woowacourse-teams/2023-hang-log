package hanglog.member.presentation;

import hanglog.member.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login/oauth2")
public class OAuthLoginController {

    private final AuthService authService;

    public OAuthLoginController(final AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/code/{provider}")
    public ResponseEntity<Void> loginOAuth(@PathVariable final String provider, @RequestParam final String code) {
        final Long memberId = authService.login(provider, code);
        return ResponseEntity.ok().build();
    }
}
