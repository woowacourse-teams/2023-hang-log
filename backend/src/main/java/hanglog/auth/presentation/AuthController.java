package hanglog.auth.presentation;

import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.HttpStatus.CREATED;

import hanglog.auth.Auth;
import hanglog.auth.domain.MemberTokens;
import hanglog.auth.dto.AccessTokenResponse;
import hanglog.auth.dto.LoginRequest;
import hanglog.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    public static final int COOKIE_AGE_SECONDS = 604800;

    private final AuthService authService;

    @PostMapping("/login/{provider}")
    public ResponseEntity<AccessTokenResponse> login(
            @PathVariable final String provider,
            @RequestBody final LoginRequest loginRequest,
            final HttpServletResponse response
    ) {
        final MemberTokens memberTokens = authService.login(provider, loginRequest.getCode());
        final ResponseCookie cookie = ResponseCookie.from("refresh-token", memberTokens.getRefreshToken())
                .maxAge(COOKIE_AGE_SECONDS)
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();
        response.addHeader(SET_COOKIE, cookie.toString());
        return ResponseEntity.status(CREATED).body(new AccessTokenResponse(memberTokens.getAccessToken()));
    }

    @PostMapping("/token")
    public ResponseEntity<AccessTokenResponse> extendLogin(
            @CookieValue("refresh-token") final String refreshToken,
            @RequestHeader("Authorization") final String authorizationHeader
    ) {
        final String renewalRefreshToken = authService.renewalAccessToken(refreshToken, authorizationHeader);
        return ResponseEntity.status(CREATED).body(new AccessTokenResponse(renewalRefreshToken));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@Auth final Long memberId) {
        authService.removeMemberRefreshToken(memberId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/account")
    public ResponseEntity<Void> deleteAccount(@Auth final Long memberId) {
        authService.deleteAccount(memberId);
        return ResponseEntity.noContent().build();
    }
}
