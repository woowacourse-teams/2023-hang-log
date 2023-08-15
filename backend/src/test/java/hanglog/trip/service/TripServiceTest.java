package hanglog.trip.service;

import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;
import static hanglog.trip.fixture.CityFixture.LONDON;
import static hanglog.trip.fixture.CityFixture.PARIS;
import static hanglog.trip.fixture.TripFixture.LONDON_TRIP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import hanglog.global.exception.BadRequestException;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.TripCity;
import hanglog.trip.domain.repository.CityRepository;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.dto.request.TripUpdateRequest;
import hanglog.trip.dto.response.TripDetailResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

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

        // when
        final Long actualId = tripService.save(tripCreateRequest);

        // then
        assertThat(actualId).isEqualTo(1L);
    }

    @DisplayName("올바르지 않은 cityId 목록을 입력받으면 예외가 발생한다.")
    @Test
    void save_UnCorrectCites() {
        // given
        final List<Long> invalidCities = List.of(1L, 3L);
        final TripCreateRequest tripCreateRequest = new TripCreateRequest(
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                invalidCities
        );

        given(cityRepository.findById(1L))
                .willReturn(Optional.of(LONDON));
        given(cityRepository.findById(3L))
                .willThrow(new BadRequestException(NOT_FOUND_TRIP_ID));

        // when & then
        assertThatThrownBy(() -> tripService.save(tripCreateRequest))
                .isInstanceOf(BadRequestException.class)
                .extracting("code")
                .isEqualTo(1001);
    }

    @DisplayName("tripId에 해당하는 여행을 반환한다.")
    @Test
    void getTrip() {
        // given
        given(tripRepository.findById(1L))
                .willReturn(Optional.of(LONDON_TRIP));

        given(tripCityRepository.findByTripId(1L))
                .willReturn(List.of(new TripCity(LONDON_TRIP, PARIS), new TripCity(LONDON_TRIP, LONDON)));

        // when
        final TripDetailResponse actual = tripService.getTripDetail(1L);

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(TripDetailResponse.of(LONDON_TRIP, List.of(PARIS, LONDON)));
    }

    @DisplayName("update 호출 시 id를 검증하고 save 메서드를 호출한다.")
    @Test
    void update() {
        // given
        final TripUpdateRequest updateRequest = new TripUpdateRequest(
                "변경된 타이틀",
                "https://hanglog.com/img/default-image.png",
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 5),
                "추가된 여행 설명",
                List.of(1L, 2L)
        );

        given(tripRepository.findById(LONDON_TRIP.getId()))
                .willReturn(Optional.of(LONDON_TRIP));
        given(cityRepository.findById(1L))
                .willReturn(Optional.of(PARIS));
        given(cityRepository.findById(2L))
                .willReturn(Optional.of(LONDON));

        // when
        tripService.update(LONDON_TRIP.getId(), updateRequest);

        // then
        verify(tripRepository).findById(LONDON_TRIP.getId());
        verify(tripRepository).save(any(Trip.class));
    }

    @DisplayName("유효하지 않은 TripId를 삭제할 시 예외가 발생한다.")
    @Test
    void delete_InvalidTripId() {
        // given
        final Long invalidTripId = 2L;

        given(tripRepository.findById(invalidTripId))
                .willThrow(new BadRequestException(NOT_FOUND_TRIP_ID));

        // when & then
        assertThatThrownBy(() -> tripService.delete(invalidTripId))
                .isInstanceOf(BadRequestException.class)
                .extracting("code")
                .isEqualTo(1001);
    }

    @Nested
    @DisplayName("Trip 일정 변경 시 DayLog 업데이트 테스트")
    class UpdateTripTests {

        TripUpdateRequest updateRequest;
        Trip trip = Trip.of(
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
                    "https://hanglog.com/img/default-image.png",
                    LocalDate.of(2023, 7, 1),
                    LocalDate.of(2023, 7, 3),
                    "",
                    null,
                    new ArrayList<>(List.of(dayLog1, dayLog2, dayLog3, extraDayLog))
            );
        }

        void changeDate(final int startDay, final int endDay) {
            updateRequest = new TripUpdateRequest(
                    "변경된 타이틀",
                    "https://hanglog.com/img/default-image.png",
                    LocalDate.of(2023, 7, startDay),
                    LocalDate.of(2023, 7, endDay),
                    "추가된 여행 설명",
                    List.of(1L, 2L)
            );

            final Trip updatedTrip = new Trip(
                    trip.getId(),
                    updateRequest.getImageUrl(),
                    updateRequest.getTitle(),
                    updateRequest.getStartDate(),
                    updateRequest.getEndDate(),
                    updateRequest.getDescription(),
                    null,
                    trip.getDayLogs()
            );

            given(tripRepository.findById(trip.getId()))
                    .willReturn(Optional.of(trip));
            given(tripRepository.save(any(Trip.class)))
                    .willReturn(updatedTrip);
            given(cityRepository.findById(1L))
                    .willReturn(Optional.of(PARIS));
            given(cityRepository.findById(2L))
                    .willReturn(Optional.of(LONDON));
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
            given(tripRepository.findById(trip.getId()))
                    .willReturn(Optional.of(new Trip(
                                            trip.getId(),
                                            trip.getTitle(),
                                            trip.getImageName(),
                                            updateRequest.getStartDate(),
                                            updateRequest.getEndDate(),
                                            trip.getDescription(),
                                            null,
                                            List.of(
                                                    dayLog1,
                                                    dayLog2,
                                                    extraDayLog
                                            )
                                    )
                            )
                    );

            // when
            tripService.update(trip.getId(), updateRequest);
            final List<DayLog> actualDayLogs = tripRepository.findById(trip.getId()).get().getDayLogs();

            // then
            assertSoftly(
                    softly -> {
                        softly.assertThat(actualDayLogs.size()).isEqualTo(3);
                        softly.assertThat(actualDayLogs).containsExactly(dayLog1, dayLog2, extraDayLog);
                    }
            );
        }

        @DisplayName("변경된 일정이 늘어난 경우")
        @Test
        void update_IncreasePeriod() {
            // given
            changeDate(1, 5);
            given(tripRepository.findById(trip.getId()))
                    .willReturn(Optional.of(new Trip(
                                            trip.getId(),
                                            trip.getTitle(),
                                            trip.getImageName(),
                                            updateRequest.getStartDate(),
                                            updateRequest.getEndDate(),
                                            trip.getDescription(),
                                            null,
                                            List.of(
                                                    dayLog1,
                                                    dayLog2,
                                                    dayLog3,
                                                    DayLog.generateEmpty(4, trip),
                                                    DayLog.generateEmpty(5, trip),
                                                    extraDayLog
                                            )
                                    )
                            )
                    );

            // when
            tripService.update(trip.getId(), updateRequest);
            final List<DayLog> actualDayLogs = tripRepository.findById(trip.getId()).get().getDayLogs();

            // then
            assertSoftly(
                    softly -> {
                        softly.assertThat(actualDayLogs.size()).isEqualTo(6);
                        softly.assertThat(actualDayLogs)
                                .usingRecursiveComparison()
                                .ignoringFields("trip")
                                .isEqualTo(List.of(
                                        dayLog1,
                                        dayLog2,
                                        dayLog3,
                                        DayLog.generateEmpty(4, trip),
                                        DayLog.generateEmpty(5, trip),
                                        extraDayLog
                                ));
                    }
            );
        }
    }
}
