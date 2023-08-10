package hanglog.member.presentation;

import hanglog.member.dto.AccessTokenResponse;
import hanglog.member.dto.MemberTokens;
import hanglog.member.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login/{provider}")
    public ResponseEntity<AccessTokenResponse> loginOAuth(
            @PathVariable final String provider,
            @RequestParam final String code,
            HttpServletResponse response
    ) {
        final MemberTokens memberTokens = authService.login(provider, code);
        final Cookie cookie = new Cookie("refresh-token", memberTokens.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(new AccessTokenResponse(memberTokens.getAccessToken()));
    }

    @PostMapping("/token")
    public ResponseEntity<AccessTokenResponse> extendLogin(
            @CookieValue("refresh-token") String refreshToken,
            @RequestBody final String accessToken,
            HttpServletResponse response
    ) {
        final MemberTokens memberTokens = authService.renewalAccessToken(refreshToken, accessToken);
        final Cookie cookie = new Cookie("refresh-token", memberTokens.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(new AccessTokenResponse(memberTokens.getAccessToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue("refresh-token") String refreshToken,
            @RequestBody final String accessToken
    ) {
        authService.removeMemberRefreshToken(refreshToken, accessToken);
        return ResponseEntity.ok().build();
    }
}
