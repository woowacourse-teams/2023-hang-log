package hanglog.like.service;

import static hanglog.integration.IntegrationFixture.TRIP_CREATE_REQUEST;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import hanglog.integration.service.RedisServiceIntegrationTest;
import hanglog.like.domain.Likes;
import hanglog.like.domain.repository.LikeRepository;
import hanglog.like.dto.request.LikeRequest;
import hanglog.trip.service.TripService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class LikeSyncSchedulerTest extends RedisServiceIntegrationTest {

    @Autowired
    private LikeSyncScheduler likeSyncScheduler;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private TripService tripService;

    @Autowired
    private LikeService likeService;

    private Long memberId;
    private Long tripId1;
    private Long tripId2;

    @BeforeEach
    void setUp() {
        memberId = member.getId();
        tripId1 = tripService.save(memberId, TRIP_CREATE_REQUEST);
        tripId2 = tripService.save(memberId, TRIP_CREATE_REQUEST);
    }

    @DisplayName("Redis에서 업데이트 된 좋아요 값을 DB에 저장한다. (기존에 저장되어 있는 TripId 1 삭제하고 TripId 2 데이터만 남는다.)")
    @Test
    void writeBackLikeCache() {
        // given
        final Likes likeTripId1 = new Likes(tripId1, memberId);
        likeRepository.save(likeTripId1);

        likeService.update(memberId, tripId1, new LikeRequest(false));
        likeService.update(memberId, tripId2, new LikeRequest(true));

        // when
        likeSyncScheduler.writeBackLikeCache();
        final List<Likes> likes = likeRepository.findAll();

        // then
        assertSoftly(softly -> {
            softly.assertThat(likes.size()).isEqualTo(1);
            softly.assertThat(likes.get(0)).usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(new Likes(tripId2, memberId));
        });
    }
}
