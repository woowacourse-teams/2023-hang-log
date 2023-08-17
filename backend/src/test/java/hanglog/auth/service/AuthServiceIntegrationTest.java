package hanglog.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import hanglog.auth.domain.BearerAuthorizationExtractor;
import hanglog.auth.domain.JwtProvider;
import hanglog.auth.domain.oauthprovider.OauthProviders;
import hanglog.auth.domain.repository.RefreshTokenRepository;
import hanglog.global.ServiceIntegrationTest;
import hanglog.trip.domain.repository.TripRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import({
        AuthService.class,
        OauthProviders.class,
        JwtProvider.class,
        BearerAuthorizationExtractor.class
})
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

    @DisplayName("멤버를 삭제한다.")
    @Test
    void deleteAccount() {
        // when & then
        assertThat(memberRepository.findById(member.getId()).isPresent()).isTrue();
        assertDoesNotThrow(() -> authService.deleteAccount(member.getId()));
        assertThat(memberRepository.findById(member.getId()).isEmpty()).isTrue();
    }
}
