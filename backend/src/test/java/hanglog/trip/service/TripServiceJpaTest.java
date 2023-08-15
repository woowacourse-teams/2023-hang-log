package hanglog.trip.service;

import static hanglog.global.IntegrationFixture.EDINBURGH;
import static hanglog.global.IntegrationFixture.END_DATE;
import static hanglog.global.IntegrationFixture.LAHGON_TRIP;
import static hanglog.global.IntegrationFixture.LONDON;
import static hanglog.global.IntegrationFixture.PARIS;
import static hanglog.global.IntegrationFixture.START_DATE;
import static hanglog.global.IntegrationFixture.TOKYO;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_CITY_ID;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import hanglog.global.ServiceJpaTest;
import hanglog.global.exception.BadRequestException;
import hanglog.trip.domain.repository.CityRepository;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.dto.request.TripUpdateRequest;
import hanglog.trip.dto.response.TripDetailResponse;
import hanglog.trip.dto.response.TripResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(TripService.class)
public class TripServiceJpaTest extends ServiceJpaTest {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private TripCityRepository tripCityRepository;

    @Autowired
    private TripService tripService;

    final TripCreateRequest tripCreateRequest = new TripCreateRequest(
            START_DATE,
            END_DATE,
            Arrays.asList(LONDON.getId(), EDINBURGH.getId())
    );

    @BeforeEach
    void setUp() {
        tripRepository.deleteAll();
    }

    @DisplayName("Trip이 정상적으로 생성된다.")
    @Test
    void save() {
        // when
        final Long tripId = tripService.save(tripCreateRequest);

        // then
        assertThat(tripId).isNotNull();
    }

    @DisplayName("Trip 저장 시 존재하지 않는 cityId일 경우, NOT_FOUND_CITY_ID 예외가 발생한다.")
    @Test
    void save_InvalidCity() {
        // given
        final TripCreateRequest badRequest = new TripCreateRequest(
                START_DATE,
                END_DATE,
                Arrays.asList(LONDON.getId(), EDINBURGH.getId(), TOKYO.getId())
        );

        // when & then
        assertThatThrownBy(() -> tripService.save(badRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(NOT_FOUND_CITY_ID.getMessage());
    }

    @DisplayName("멤버의 모든 Trip을 조회한다.")
    @Test
    void getAllTrips() {
        // given
        final Long tripId1 = tripService.save(tripCreateRequest);
        final Long tripId2 = tripService.save(tripCreateRequest);

        // when
        final List<TripResponse> tripResponses = tripService.getAllTrips();

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(tripResponses.size()).isEqualTo(2);
                    softly.assertThat(tripResponses).extracting("id")
                            .contains(tripId1, tripId2);
                }
        );
    }

    @DisplayName("TripId에 해당하는 Trip을 조회한다.")
    @Test
    void getTripDetail() {
        // given
        final Long tripId = tripService.save(tripCreateRequest);

        // when
        final TripDetailResponse tripDetailResponse = tripService.getTripDetail(tripId);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(tripDetailResponse.getId()).isEqualTo(tripId);
                    softly.assertThat(tripDetailResponse)
                            .usingRecursiveComparison()
                            .ignoringFields("id", "dayLogs")
                            .ignoringFieldsMatchingRegexes(".*latitude", ".*longitude")
                            .isEqualTo(TripDetailResponse.of(LAHGON_TRIP, List.of(LONDON, EDINBURGH)));
                }
        );
    }

    @DisplayName("Trip 조회 시 존재하지 않는 TripId일 경우, NOT_FOUND_TRIP_ID 예외가 발생한다.")
    @Test
    void getTripDetail_InvalidTripId() {
        // given
        final Long invalidTripId = 1L;

        // when & then
        assertThatThrownBy(() -> tripService.getTripDetail(invalidTripId))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(NOT_FOUND_TRIP_ID.getMessage());
    }

    @DisplayName("TripId에 해당하는 Trip을 정상적으로 업데이트한다.")
    @Test
    void update() {
        // given
        final Long tripId = tripService.save(tripCreateRequest);

        final String updatedTitle = "수정된 여행 제목";
        final String updatedDescription = "매번 색다르고 즐거운 서유럽 여행";
        final TripUpdateRequest tripUpdateRequest = new TripUpdateRequest(
                updatedTitle,
                null,
                LocalDate.of(2023, 8, 1),
                LocalDate.of(2023, 8, 3),
                updatedDescription,
                List.of(1L, 2L, 3L)
        );

        // when & then
        assertDoesNotThrow(() -> tripService.update(tripId, tripUpdateRequest));
        assertThat(tripService.getTripDetail(tripId))
                .extracting("title", "description")
                .containsExactly(updatedTitle, updatedDescription);
        assertThat(tripService.getTripDetail(tripId).getCities())
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*latitude", ".*longitude")
                .isEqualTo(List.of(LONDON, EDINBURGH, PARIS));
    }

    @DisplayName("Trip 수정 시 유효하지 않은 tripId일 경우, NOT_FOUND_TRIP_ID 예외가 발생한다.")
    @Test
    void update_InvalidTripId() {
        // given
        final Long invalidTripId = 1L;
        final String updatedTitle = "수정된 여행 제목";
        final String updatedDescription = "매번 색다르고 즐거운 서유럽 여행";
        final TripUpdateRequest tripUpdateRequest = new TripUpdateRequest(
                updatedTitle,
                null,
                LocalDate.of(2023, 8, 1),
                LocalDate.of(2023, 8, 3),
                updatedDescription,
                List.of(1L, 2L, 3L)
        );

        // when & then
        assertThatThrownBy(() -> tripService.update(invalidTripId, tripUpdateRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(NOT_FOUND_TRIP_ID.getMessage());
    }

    @DisplayName("Trip 수정 시 유효하지 않은 cityId 경우, NOT_FOUND_CITY_ID 예외가 발생한다.")
    @Test
    void update_InvalidCityId() {
        // given
        final Long tripId = tripService.save(tripCreateRequest);
        final List<Long> invalidCityIds = List.of(4L);

        final String updatedTitle = "수정된 여행 제목";
        final String updatedDescription = "매번 색다르고 즐거운 서유럽 여행";
        final TripUpdateRequest tripUpdateRequest = new TripUpdateRequest(
                updatedTitle,
                null,
                LocalDate.of(2023, 8, 1),
                LocalDate.of(2023, 8, 3),
                updatedDescription,
                invalidCityIds
        );

        // when & then
        assertThatThrownBy(() -> tripService.update(tripId, tripUpdateRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(NOT_FOUND_CITY_ID.getMessage());
    }

    @DisplayName("TripId에 해당하는 Trip이 정상적으로 삭제된다.")
    @Test
    void delete() {
        // given
        final Long tripId = tripService.save(tripCreateRequest);

        // when & then
        assertDoesNotThrow(() -> tripService.delete(tripId));
    }

    @DisplayName("Trip 삭제 시 유효하지 않은 tripId일 경우, NOT_FOUND_TRIP_ID 예외가 발생한다.")
    @Test
    void delete_InvalidTripId() {
        // given
        final Long invalidTripId = 1L;

        // when & then
        assertThatThrownBy(() -> tripService.delete(invalidTripId))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(NOT_FOUND_TRIP_ID.getMessage());
    }
}
