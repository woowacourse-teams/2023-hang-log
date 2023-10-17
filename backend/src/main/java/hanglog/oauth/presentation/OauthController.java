package hanglog.oauth.presentation;

import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.HttpStatus.CREATED;

import hanglog.auth.Auth;
import hanglog.auth.MemberOnly;
import hanglog.auth.domain.Accessor;
import hanglog.oauth.domain.MemberTokens;
import hanglog.oauth.dto.AccessTokenResponse;
import hanglog.oauth.dto.LoginRequest;
import hanglog.oauth.service.OauthService;
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
public class OauthController {

    public static final int COOKIE_AGE_SECONDS = 604800;

    private final OauthService oauthService;

    @PostMapping("/login/{provider}")
    public ResponseEntity<AccessTokenResponse> login(
            @PathVariable final String provider,
            @RequestBody final LoginRequest loginRequest,
            final HttpServletResponse response
    ) {
        final MemberTokens memberTokens = oauthService.login(provider, loginRequest.getCode());
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
        final String renewalRefreshToken = oauthService.renewalAccessToken(refreshToken, authorizationHeader);
        return ResponseEntity.status(CREATED).body(new AccessTokenResponse(renewalRefreshToken));
    }

    @DeleteMapping("/logout")
    @MemberOnly
    public ResponseEntity<Void> logout(
            @Auth final Accessor accessor,
            @CookieValue("refresh-token") final String refreshToken) {
        oauthService.removeRefreshToken(refreshToken);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/account")
    @MemberOnly
    public ResponseEntity<Void> deleteAccount(@Auth final Accessor accessor) {
        oauthService.deleteAccount(accessor.getMemberId());
        return ResponseEntity.noContent().build();
    }
}
