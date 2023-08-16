package hanglog.auth.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import hanglog.auth.domain.BearerAuthorizationExtractor;
import hanglog.auth.domain.JwtProvider;
import hanglog.auth.domain.oauthprovider.OauthProviders;
import hanglog.auth.domain.repository.RefreshTokenRepository;
import hanglog.global.ServiceIntegrationTest;
import hanglog.trip.domain.repository.TripRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(AuthService.class)
class AuthServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private OauthProviders oauthProviders;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private BearerAuthorizationExtractor bearerExtractor;
    @Autowired
    private AuthService authService;

    @Test
    void deleteAccount() {
        // when
        authService.deleteAccount(member.getId());

        // then
        assertDoesNotThrow(() -> authService.deleteAccount(member.getId()));
    }
}
