package hanglog.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import hanglog.auth.domain.BearerAuthorizationExtractor;
import hanglog.auth.domain.JwtProvider;
import hanglog.auth.domain.oauthprovider.OauthProviders;
import hanglog.auth.domain.repository.RefreshTokenRepository;
import hanglog.auth.service.AuthService;
import hanglog.member.domain.repository.MemberRepository;
import hanglog.trip.domain.repository.SharedTripRepository;
import hanglog.trip.domain.repository.PublishedTripRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.infrastructure.CustomTripRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Import;

@Import({
        AuthService.class,
        OauthProviders.class,
        JwtProvider.class,
        BearerAuthorizationExtractor.class,
        CustomTripRepositoryImpl.class
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
    @Autowired
    private PublishedTripRepository publishedTripRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SharedTripRepository sharedTripRepository;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private EntityManager entityManager;

    @DisplayName("멤버를 삭제한다.")
    @Test
    void deleteAccount() {
        // when & then
        assertThat(memberRepository.findById(member.getId())).isPresent();
        assertDoesNotThrow(() -> authService.deleteAccount(member.getId()));
        entityManager.flush();
        entityManager.clear();

        assertThat(memberRepository.findById(member.getId())).isEmpty();
    }
}
