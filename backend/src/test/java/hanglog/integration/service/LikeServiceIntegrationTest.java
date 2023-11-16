package hanglog.integration.service;

import static hanglog.integration.IntegrationFixture.TRIP_CREATE_REQUEST;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import hanglog.like.dto.request.LikeRequest;
import hanglog.like.service.LikeService;
import hanglog.trip.service.TripService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class LikeServiceIntegrationTest extends RedisServiceIntegrationTest {

    @Autowired
    private TripService tripService;

    @Autowired
    private LikeService likeService;

    @DisplayName("해당 게시물의 좋아요 여부를 변경할 수 있다.")
    @Test
    void update() {
        // given
        final LikeRequest likeTrueRequest = new LikeRequest(true);
        final LikeRequest likeFalseRequest = new LikeRequest(false);

        final Long tripId = tripService.save(member.getId(), TRIP_CREATE_REQUEST);

        // when & then
        assertSoftly(softly -> {
            softly.assertThat(likeService.check(member.getId(), tripId)).isFalse();
            likeService.update(member.getId(), tripId, likeTrueRequest);
            softly.assertThat(likeService.check(member.getId(), tripId)).isTrue();
            likeService.update(member.getId(), tripId, likeFalseRequest);
            softly.assertThat(likeService.check(member.getId(), tripId)).isFalse();
        });
    }
}
