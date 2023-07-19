package hanglog.trip.service;

import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.repository.CityRepository;
import hanglog.trip.domain.repository.DayLogRepository;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.dto.request.TripUpdateRequest;
import hanglog.trip.dto.response.TripResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static hanglog.trip.fixture.CityFixture.LONDON;
import static hanglog.trip.fixture.CityFixture.PARIS;
import static hanglog.trip.fixture.TripFixture.LONDON_TRIP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Transactional
class TripServiceTest {

    @InjectMocks
    private TripService tripService;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private TripCityRepository tripCityRepository;

    @Mock
    private DayLogRepository dayLogRepository;

    @DisplayName("여행을 생성한 후 tripId를 반환한다.")
    @Test
    void save() {
        // given
        final TripCreateRequest tripCreateRequest = new TripCreateRequest(
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                List.of(1L, 2L)
        );

        given(cityRepository.findById(1L))
                .willReturn(Optional.of(LONDON));
        given(cityRepository.findById(2L))
                .willReturn(Optional.of(PARIS));
        given(tripRepository.save(any(Trip.class)))
                .willReturn(LONDON_TRIP);
        given(dayLogRepository.saveAll(anyList()))
                .willReturn(Collections.emptyList());

        // when
        final Long actualId = tripService.save(tripCreateRequest);

        // then
        assertThat(actualId).isEqualTo(1L);
    }

    @DisplayName("올바르지 않은 cityId 목록을 입력받으면 예외가 발생한다.")
    @Test
    void save_UnCorrectCites() {
        // given
        List<Long> invalidCities = List.of(1L, 3L);
        final TripCreateRequest tripCreateRequest = new TripCreateRequest(
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                invalidCities
        );

        given(cityRepository.findById(1L))
                .willReturn(Optional.of(LONDON));
        given(cityRepository.findById(3L))
                .willThrow(new IllegalArgumentException("해당하는 여행이 존재하지 않습니다."));

        // when & then
        assertThatThrownBy(() -> tripService.save(tripCreateRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("tripId에 해당하는 여행을 반환한다.")
    @Test
    void getTrip() {
        // given
        given(tripRepository.findById(1L))
                .willReturn(Optional.of(LONDON_TRIP));

        // when
        TripResponse actual = tripService.getTrip(1L);

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(TripResponse.of(LONDON_TRIP));
    }

    @DisplayName("update 호출 시 id를 검증하고 save 메서드를 호출한다.")
    @Test
    void update() {
        // given
        final TripUpdateRequest updateRequest = new TripUpdateRequest(
                "변경된 타이틀",
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 5),
                "추가된 여행 설명",
                List.of(1L, 2L, 3L)
        );

        given(tripRepository.findById(LONDON_TRIP.getId()))
                .willReturn(Optional.of(LONDON_TRIP));

        // when
        tripService.update(LONDON_TRIP.getId(), updateRequest);

        // then
        verify(tripRepository).findById(LONDON_TRIP.getId());
        verify(tripRepository).save(any(Trip.class));
    }

    @Nested
    @DisplayName("Trip 일정 변경 시 DayLog 업데이트 테스트")
    class UpdateTripTests {

        TripUpdateRequest updateRequest;
        Trip trip = new Trip(
                "파리 여행",
                LocalDate.of(2023, 7, 1),
                LocalDate.of(2023, 7, 3)
        );
        DayLog dayLog1 = new DayLog(2L, "파리 여행 1일차", 1, trip, List.of());
        DayLog dayLog2 = new DayLog(3L, "파리 여행 2일차", 2, trip, List.of());
        DayLog dayLog3 = new DayLog(4L, "파리 여행 3일차", 3, trip, List.of());
        DayLog extraDayLog = new DayLog(5L, "경비 페이지", 4, trip, List.of());

        @BeforeEach
        void setUp() {
            trip = new Trip(
                    2L,
                    "파리 여행",
                    LocalDate.of(2023, 7, 1),
                    LocalDate.of(2023, 7, 3),
                    "",
                    new ArrayList<>(List.of(dayLog1, dayLog2, dayLog3, extraDayLog))
            );
        }

        void changeDate(final int startDay, final int endDay) {
            updateRequest = new TripUpdateRequest(
                    "변경된 타이틀",
                    LocalDate.of(2023, 7, startDay),
                    LocalDate.of(2023, 7, endDay),
                    "추가된 여행 설명",
                    List.of(1L, 2L, 3L)
            );

            Trip updatedTrip = new Trip(
                    trip.getId(),
                    updateRequest.getTitle(),
                    updateRequest.getStartDate(),
                    updateRequest.getEndDate(),
                    updateRequest.getDescription(),
                    trip.getDayLogs()
            );

            given(tripRepository.findById(trip.getId()))
                    .willReturn(Optional.of(trip));
            given(tripRepository.save(any(Trip.class)))
                    .willReturn(updatedTrip);
        }

        @DisplayName("변경된 일정이 같은 경우")
        @Test
        void update_SamePeriod() {
            // given
            changeDate(2, 4);

            // when
            tripService.update(trip.getId(), updateRequest);

            // then
            assertSoftly(
                    softly -> {
                        softly.assertThat(trip.getDayLogs().size()).isEqualTo(4);
                        softly.assertThat(trip.getDayLogs()).containsExactly(dayLog1, dayLog2, dayLog3, extraDayLog);
                    }
            );
        }


        @DisplayName("변경된 일정이 줄어든 경우")
        @Test
        void update_DecreasePeriod() {
            // given
            changeDate(1, 2);

            // when
            tripService.update(trip.getId(), updateRequest);

            // then
            assertSoftly(
                    softly -> {
                        softly.assertThat(trip.getDayLogs().size()).isEqualTo(3);
                        softly.assertThat(trip.getDayLogs()).containsExactly(dayLog1, dayLog2, extraDayLog);
                    }
            );
        }

        @DisplayName("변경된 일정이 늘어난 경우")
        @Test
        void update_IncreasePeriod() {
            // given
            changeDate(1, 4);

            // when
            tripService.update(trip.getId(), updateRequest);

            // then
            assertSoftly(
                    softly -> {
                        softly.assertThat(trip.getDayLogs().size()).isEqualTo(5);
                        softly.assertThat(trip.getDayLogs())
                                .usingRecursiveComparison()
                                .ignoringFields("trip")
                                .isEqualTo(List.of(dayLog1, dayLog2, dayLog3, DayLog.empty(4, trip), extraDayLog));
                    }
            );
        }
    }

    @DisplayName("유효하지 않은 TripId를 삭제할 시 예외가 발생한다.")
    @Test
    void delete_InvalidTripId() {
        // given
        final Long invalidTripId = 2L;

        given(tripRepository.findById(invalidTripId))
                .willThrow(IllegalStateException.class);

        // when & then
        assertThatThrownBy(() -> tripService.delete(invalidTripId))
                .isInstanceOf(IllegalStateException.class);
    }
}
