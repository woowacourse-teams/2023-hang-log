package hanglog.integration.service;

import static hanglog.integration.IntegrationFixture.TRIP_CREATE_REQUEST;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import hanglog.like.domain.LikeCount;
import hanglog.like.domain.repository.LikeCountRepository;
import hanglog.like.domain.repository.MemberLikeRepository;
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

    @Autowired
    private MemberLikeRepository memberLikeRepository;

    @Autowired
    private LikeCountRepository likeCountRepository;

    @DisplayName("해당 게시물의 좋아요 여부를 변경할 수 있다.")
    @Test
    void update() {
        // given
        final LikeRequest likeTrueRequest = new LikeRequest(true);
        final LikeRequest likeFalseRequest = new LikeRequest(false);

        final Long tripId = tripService.save(member.getId(), TRIP_CREATE_REQUEST);

        // when & then
        assertSoftly(softly -> {
            softly.assertThat(memberLikeRepository.findById(member.getId())).isEmpty();

            likeService.update(member.getId(), tripId, likeTrueRequest);
            softly.assertThat(memberLikeRepository.findById(member.getId())).isPresent();
            softly.assertThat(memberLikeRepository.findById(member.getId())
                    .get()
                    .getLikeStatusForTrip()
                    .get(tripId)).isTrue();
            softly.assertThat(likeCountRepository.findById(tripId)).isPresent();
            softly.assertThat(likeCountRepository.findById(tripId)).get().usingRecursiveComparison()
                    .isEqualTo(new LikeCount(tripId, 1L));

            likeService.update(member.getId(), tripId, likeFalseRequest);
            softly.assertThat(memberLikeRepository.findById(member.getId())).isPresent();
            softly.assertThat(memberLikeRepository.findById(member.getId())
                    .get()
                    .getLikeStatusForTrip()
                    .get(tripId)).isFalse();
            softly.assertThat(likeCountRepository.findById(tripId)).isPresent();
            softly.assertThat(likeCountRepository.findById(tripId)).get().usingRecursiveComparison()
                    .isEqualTo(new LikeCount(tripId, 0L));
        });
    }
}
