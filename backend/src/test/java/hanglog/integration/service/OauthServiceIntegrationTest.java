package hanglog.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import hanglog.member.domain.repository.MemberRepository;
import hanglog.oauth.domain.BearerAuthorizationExtractor;
import hanglog.oauth.domain.JwtProvider;
import hanglog.oauth.domain.oauthprovider.OauthProviders;
import hanglog.oauth.domain.repository.RefreshTokenRepository;
import hanglog.oauth.service.OauthService;
import hanglog.trip.domain.repository.PublishedTripRepository;
import hanglog.trip.domain.repository.SharedTripRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.infrastructure.CustomTripRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Import;

@Import({
        OauthService.class,
        OauthProviders.class,
        JwtProvider.class,
        BearerAuthorizationExtractor.class,
        CustomTripRepositoryImpl.class
})
class OauthServiceIntegrationTest extends ServiceIntegrationTest {

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
    private OauthService oauthService;
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
        assertDoesNotThrow(() -> oauthService.deleteAccount(member.getId()));
        entityManager.flush();
        entityManager.clear();

        assertThat(memberRepository.findById(member.getId())).isEmpty();
    }
}
