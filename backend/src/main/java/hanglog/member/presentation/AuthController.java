package hanglog.member.presentation;

import hanglog.member.dto.AccessTokenRequest;
import hanglog.member.dto.AccessTokenResponse;
import hanglog.member.dto.LoginRequest;
import hanglog.member.domain.MemberTokens;
import hanglog.member.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login/{provider}")
    public ResponseEntity<AccessTokenResponse> login(
            @PathVariable final String provider,
            @RequestBody final LoginRequest loginRequest,
            final HttpServletResponse response
    ) {
        final MemberTokens memberTokens = authService.login(provider, loginRequest.getCode());
        final Cookie cookie = new Cookie("refresh-token", memberTokens.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(new AccessTokenResponse(memberTokens.getAccessToken()));
    }

    @PostMapping("/token")
    public ResponseEntity<AccessTokenResponse> extendLogin(
            @CookieValue("refresh-token") String refreshToken,
            @RequestBody final AccessTokenRequest request,
            final HttpServletResponse response
    ) {
        final MemberTokens memberTokens = authService.renewalAccessToken(refreshToken, request.getAccessToken());
        final Cookie cookie = new Cookie("refresh-token", memberTokens.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(new AccessTokenResponse(memberTokens.getAccessToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue("refresh-token") String refreshToken,
            @RequestBody final AccessTokenRequest request
    ) {
        authService.removeMemberRefreshToken(refreshToken, request.getAccessToken());
        return ResponseEntity.ok().build();
    }
}
