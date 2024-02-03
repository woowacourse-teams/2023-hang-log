package hanglog.integration.service;

import static hanglog.integration.IntegrationFixture.TRIP_CREATE_REQUEST;
import static hanglog.like.domain.LikeRedisConstants.generateLikeKey;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import hanglog.like.domain.Likes;
import hanglog.like.domain.repository.LikeRepository;
import hanglog.like.dto.request.LikeRequest;
import hanglog.like.service.LikeService;
import hanglog.trip.service.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

class LikeServiceIntegrationTest extends RedisServiceIntegrationTest {

    @Autowired
    private TripService tripService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private LikeRepository likeRepository;

    private SetOperations<String, Object> opsForSet;
    private Long memberId;
    private Long tripId;
    private String key;

    @BeforeEach
    void setUp() {
        opsForSet = redisTemplate.opsForSet();
        memberId = member.getId();
        tripId = tripService.save(memberId, TRIP_CREATE_REQUEST);
        key = generateLikeKey(tripId);
    }

    @DisplayName("해당 게시물의 좋아요 여부를 변경할 수 있다.")
    @Test
    void update() {
        // given
        final LikeRequest likeTrueRequest = new LikeRequest(true);
        final LikeRequest likeFalseRequest = new LikeRequest(false);

        // when & then
        assertSoftly(softly -> {
            softly.assertThat(opsForSet.isMember(key, memberId)).isEqualTo(FALSE);

            likeService.update(memberId, tripId, likeTrueRequest);
            softly.assertThat(opsForSet.isMember(key, memberId)).isEqualTo(TRUE);

            likeService.update(memberId, tripId, likeFalseRequest);
            softly.assertThat(opsForSet.isMember(key, memberId)).isEqualTo(FALSE);
        });
    }

    @DisplayName("이미 여행에 좋아요를 누른 유저가 다시 좋아요 추가 요청을 보내면 무시하고 값을 그대로 유지한다.")
    @Test
    void update_TrueRequestWhenIsMember() {
        // given
        final LikeRequest likeTrueRequest = new LikeRequest(true);

        // when
        likeService.update(memberId, tripId, likeTrueRequest);

        // then
        assertSoftly(softly -> {
            softly.assertThat(opsForSet.isMember(key, memberId)).isEqualTo(TRUE);
            likeService.update(memberId, tripId, likeTrueRequest);
            softly.assertThat(opsForSet.isMember(key, memberId)).isEqualTo(TRUE);
        });
    }

    @DisplayName("여행에 좋아요를 누르지 않은 유저가 좋아요 삭제 요청을 보내면 무시하고 값을 그대로 유지한다.")
    @Test
    void update_FalseRequestWhenIsNotMember() {
        // given
        final LikeRequest likeFalseRequest = new LikeRequest(false);

        // when
        likeService.update(memberId, tripId, likeFalseRequest);

        // then
        assertSoftly(softly -> {
            softly.assertThat(opsForSet.isMember(key, memberId)).isEqualTo(FALSE);
            likeService.update(memberId, tripId, likeFalseRequest);
            softly.assertThat(opsForSet.isMember(key, memberId)).isEqualTo(FALSE);
        });
    }
    
    @DisplayName("TripId 키가 Redis에 존재하지 않을 경우 DB에 조회해서 업데이트한다.")
    @Test
    void update_WhenTripIdIsNotExistInRedis() {
        // given
        final LikeRequest likeTrueRequest = new LikeRequest(true);

        final Likes likes = new Likes(tripId, memberId);
        likeRepository.save(likes);

        // when & then
        assertSoftly(softly -> {
            softly.assertThat(opsForSet.isMember(key, memberId)).isEqualTo(FALSE);
            likeService.update(memberId, tripId, likeTrueRequest);
            softly.assertThat(opsForSet.isMember(key, memberId)).isEqualTo(TRUE);
        });
    }
}
