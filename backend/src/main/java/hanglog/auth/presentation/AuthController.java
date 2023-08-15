package hanglog.auth.presentation;

import static org.springframework.http.HttpStatus.CREATED;

import hanglog.auth.domain.MemberTokens;
import hanglog.auth.dto.AccessTokenRequest;
import hanglog.auth.dto.AccessTokenResponse;
import hanglog.auth.dto.LoginRequest;
import hanglog.auth.service.AuthService;
import hanglog.auth.Auth;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
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
        return ResponseEntity.status(CREATED).body(new AccessTokenResponse(memberTokens.getAccessToken()));
    }

    @PostMapping("/token")
    public ResponseEntity<AccessTokenResponse> extendLogin(
            @CookieValue("refresh-token") final String refreshToken,
            @RequestBody final AccessTokenRequest request
    ) {
        final String renewalRefreshToken = authService.renewalAccessToken(refreshToken, request.getAccessToken());
        return ResponseEntity.status(CREATED).body(new AccessTokenResponse(renewalRefreshToken));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@Auth final Long memberId) {
        authService.removeMemberRefreshToken(memberId);
        return ResponseEntity.noContent().build();
    }
}
