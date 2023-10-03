package hanglog.integration.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.data.domain.Sort.Direction.DESC;

import hanglog.auth.domain.Accessor;
import hanglog.community.domain.recommendstrategy.RecommendStrategies;
import hanglog.community.dto.response.CommunityTripDetailResponse;
import hanglog.community.dto.response.CommunityTripListResponse;
import hanglog.community.dto.response.CommunityTripResponse;
import hanglog.community.service.CommunityService;
import hanglog.expense.service.ExpenseService;
import hanglog.trip.domain.repository.CustomDayLogRepositoryImpl;
import hanglog.trip.domain.repository.CustomTripCityRepositoryImpl;
import hanglog.trip.dto.request.PublishedStatusRequest;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.service.TripService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Import({
        TripService.class,
        CommunityService.class,
        ExpenseService.class,
        RecommendStrategies.class,
        CustomDayLogRepositoryImpl.class,
        CustomTripCityRepositoryImpl.class
})
public class CommunityServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private TripService tripService;

    @Autowired
    private CommunityService communityService;

    @BeforeEach
    void setTrips() {
        for (long i = 1; i < 21; i++) {
            tripService.save(1L,
                    new TripCreateRequest(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 2), List.of(1L))
            );
            tripService.updatePublishedStatus(i, new PublishedStatusRequest(true));
        }
    }

    @DisplayName("페이지 수 만큼 여행과 마지막 페이지 index를 가져온다")
    @Test
    void getTripsByPage() {
        // when
        final Pageable pageable = PageRequest.of(1, 10, DESC, "publishedTrip.id");
        final CommunityTripListResponse response = communityService.getTripsByPage(Accessor.member(1L), pageable);
        final List<CommunityTripResponse> tripResponses = response.getTrips();

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(tripResponses).hasSize(10);
                    softly.assertThat(response.getLastPageIndex()).isEqualTo(2L);
                    softly.assertThat(tripResponses.get(9).getId()).isEqualTo(11L);
                }
        );
    }

    @DisplayName("게스트가 여행을 방문한다.")
    @Test
    void getTripDetail_Guest() {
        // when
        final CommunityTripDetailResponse response = communityService.getTripDetail(Accessor.guest(), 1L);

        //then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.getIsLike()).isFalse();
                    softly.assertThat(response.getIsWriter()).isFalse();
                }
        );
    }

    @DisplayName("사용자가 타인의 여행을 방문한다.")
    @Test
    void getTripDetail_MemberOtherTrip() {
        // when
        final CommunityTripDetailResponse response = communityService.getTripDetail(Accessor.member(2L), 1L);

        //then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.getIsLike()).isFalse();
                    softly.assertThat(response.getIsWriter()).isFalse();
                }
        );
    }

    @DisplayName("사용자가 본인의 여행을 방문한다.")
    @Test
    void getTripDetail_MemberOwnTrip() {
        // when
        final CommunityTripDetailResponse response = communityService.getTripDetail(Accessor.member(1L), 1L);

        //then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.getIsLike()).isFalse();
                    softly.assertThat(response.getIsWriter()).isTrue();
                }
        );
    }
}
