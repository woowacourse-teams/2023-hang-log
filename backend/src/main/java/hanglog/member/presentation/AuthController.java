package hanglog.member.presentation;

import hanglog.member.domain.MemberTokens;
import hanglog.member.dto.AccessTokenRequest;
import hanglog.member.dto.AccessTokenResponse;
import hanglog.member.dto.LoginRequest;
import hanglog.member.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

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
            @CookieValue("refresh-token") final String refreshToken,
            @RequestBody final AccessTokenRequest request,
            final HttpServletResponse response
    ) {
        final String renewalRefreshToken = authService.renewalAccessToken(refreshToken, request.getAccessToken());
        return ResponseEntity.ok(new AccessTokenResponse(renewalRefreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue("refresh-token") final String refreshToken,
            @RequestBody final AccessTokenRequest request
    ) {
        authService.removeMemberRefreshToken(refreshToken, request.getAccessToken());
        return ResponseEntity.ok().build();
    }
}
