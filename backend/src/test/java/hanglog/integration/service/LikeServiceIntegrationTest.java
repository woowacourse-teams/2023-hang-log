package hanglog.integration.service;

import static hanglog.integration.IntegrationFixture.TRIP_CREATE_REQUEST;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import hanglog.community.domain.repository.LikeRepository;
import hanglog.community.dto.request.LikeRequest;
import hanglog.community.service.LikeService;
import hanglog.trip.service.TripService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import({
        TripService.class,
        LikeService.class
})
class LikeServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private TripService tripService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private LikeRepository likeRepository;

    @DisplayName("해당 게시물의 좋아요 여부를 변경할 수 있다.")
    @Test
    void update() {
        // given
        final LikeRequest likeTrueRequest = new LikeRequest(true);
        final LikeRequest likeFalseRequest = new LikeRequest(false);

        final Long tripId = tripService.save(member.getId(), TRIP_CREATE_REQUEST);

        // when & then
        assertSoftly(softly -> {
            softly.assertThat(likeRepository.existsByMemberIdAndTripId(member.getId(), tripId)).isFalse();
            likeService.update(member.getId(), tripId, likeTrueRequest);
            softly.assertThat(likeRepository.existsByMemberIdAndTripId(member.getId(), tripId)).isTrue();
            likeService.update(member.getId(), tripId, likeFalseRequest);
            softly.assertThat(likeRepository.existsByMemberIdAndTripId(member.getId(), tripId)).isFalse();
        });
    }
}
