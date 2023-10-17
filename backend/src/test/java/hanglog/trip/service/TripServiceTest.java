package hanglog.trip.service;

import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;
import static hanglog.integration.IntegrationFixture.MEMBER;
import static hanglog.share.fixture.ShareFixture.SHARED_TRIP;
import static hanglog.share.fixture.ShareFixture.TRIP_HAS_SHARED_TRIP;
import static hanglog.trip.fixture.CityFixture.LONDON;
import static hanglog.trip.fixture.CityFixture.PARIS;
import static hanglog.trip.fixture.TripFixture.LONDON_TRIP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import hanglog.city.domain.repository.CityRepository;
import hanglog.community.domain.PublishedTrip;
import hanglog.global.exception.BadRequestException;
import hanglog.member.domain.repository.MemberRepository;
import hanglog.trip.domain.repository.SharedTripRepository;
import hanglog.trip.dto.request.SharedStatusRequest;
import hanglog.trip.dto.response.SharedCodeResponse;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.repository.CustomDayLogRepository;
import hanglog.trip.domain.repository.CustomTripCityRepository;
import hanglog.trip.domain.repository.PublishedTripRepository;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.domain.type.PublishedStatusType;
import hanglog.trip.domain.type.SharedStatusType;
import hanglog.trip.dto.request.PublishedStatusRequest;
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
    private SharedTripRepository sharedTripRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private TripCityRepository tripCityRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PublishedTripRepository publishedTripRepository;

    @Mock
    private CustomDayLogRepository customDayLogRepository;

    @Mock
    private CustomTripCityRepository customTripCityRepository;

    @DisplayName("MemberId와 TripId로 여행이 존재하는지 검증한다.")
    @Test
    void validateTripByMember() {
        // given
        given(tripRepository.existsByMemberIdAndId(1L, 1L)).willReturn(true);

        // when
        tripService.validateTripByMember(1L, 1L);

        // then
        verify(tripRepository).existsByMemberIdAndId(anyLong(), anyLong());
    }

    @DisplayName("여행을 생성한 후 tripId를 반환한다.")
    @Test
    void save() {
        // given
        final TripCreateRequest tripCreateRequest = new TripCreateRequest(
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                List.of(1L, 2L)
        );

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(MEMBER));
        given(tripRepository.save(any(Trip.class)))
                .willReturn(LONDON_TRIP);
        given(cityRepository.findCitiesByIds(anyList()))
                .willReturn(List.of(PARIS, LONDON));
        doNothing().when(customDayLogRepository).saveAll(any());

        // when
        final Long actualId = tripService.save(MEMBER.getId(), tripCreateRequest);

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

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(MEMBER));
        given(cityRepository.findCitiesByIds(anyList()))
                .willThrow(new BadRequestException(NOT_FOUND_TRIP_ID));

        // when & then
        assertThatThrownBy(() -> tripService.save(MEMBER.getId(), tripCreateRequest))
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

        given(cityRepository.findCitiesByTripId(anyLong()))
                .willReturn(List.of(PARIS, LONDON));

        // when
        final TripDetailResponse actual = tripService.getTripDetail(1L);

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(TripDetailResponse.personalTrip(LONDON_TRIP, List.of(PARIS, LONDON)));
    }

    @DisplayName("update 호출 시 id를 검증하고 save 메서드를 호출한다.")
    @Test
    void update() {
        // given
        final TripUpdateRequest updateRequest = new TripUpdateRequest(
                "변경된 타이틀",
                "default-image.png",
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
        doNothing().when(customTripCityRepository).saveAll(any(), anyLong());

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

        given(tripRepository.existsById(invalidTripId)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> tripService.delete(invalidTripId))
                .isInstanceOf(BadRequestException.class)
                .extracting("code")
                .isEqualTo(1001);
    }

    @DisplayName("여행의 공유 허용상태로 변경한다.")
    @Test
    void updateSharedStatus() {
        // given
        final SharedStatusRequest sharedStatusRequest = new SharedStatusRequest(true);
        given(tripRepository.findById(anyLong()))
                .willReturn(Optional.of(TRIP_HAS_SHARED_TRIP));

        // when
        final SharedCodeResponse actual = tripService.updateSharedTripStatus(1L, sharedStatusRequest);

        //then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new SharedCodeResponse(SHARED_TRIP.getSharedCode()));
    }

    @DisplayName("공유 허용을 처음 할 경우 새로운 공유 code를 생성한다.")
    @Test
    void updateSharedStatus_CreateSharedTrip() {
        // given
        final SharedStatusRequest sharedStatusRequest = new SharedStatusRequest(true);
        given(tripRepository.findById(anyLong()))
                .willReturn(Optional.of(TRIP_HAS_SHARED_TRIP));

        // when
        final SharedCodeResponse actual = tripService.updateSharedTripStatus(1L, sharedStatusRequest);

        //then
        assertThat(actual.getSharedCode()).isNotNull();
    }

    @DisplayName("존재하지 않는 여행의 공유 상태 변경은 예외처리한다.")
    @Test
    void updateSharedStatus_NotExistTripFail() {
        // given
        final SharedStatusRequest sharedStatusRequest = new SharedStatusRequest(true);

        // when & then
        assertThatThrownBy(() -> tripService.updateSharedTripStatus(1L, sharedStatusRequest))
                .isInstanceOf(BadRequestException.class)
                .extracting("code")
                .isEqualTo(NOT_FOUND_TRIP_ID.getCode());
    }

    @DisplayName("비공개인 Trip을 공개로 변경한다.")
    @Test
    void updatePublishedStatus_FirstPublished() {
        // given
        LONDON_TRIP.changePublishedStatus(false);
        given(tripRepository.findById(LONDON_TRIP.getId()))
                .willReturn(Optional.of(LONDON_TRIP));
        given(publishedTripRepository.existsByTripId(LONDON_TRIP.getId()))
                .willReturn(false);
        final PublishedStatusRequest publishedStatusRequest = new PublishedStatusRequest(true);

        // when
        tripService.updatePublishedStatus(LONDON_TRIP.getId(), publishedStatusRequest);

        // then
        verify(publishedTripRepository).save(any(PublishedTrip.class));
        assertThat(LONDON_TRIP.getPublishedStatus()).isEqualTo(PublishedStatusType.PUBLISHED);
    }

    @DisplayName("PublishedTrip이 존재하는 비공개 Trip을 공개로 변경한다.")
    @Test
    void updatePublishedStatus_NotFirstPublished() {
        // given
        LONDON_TRIP.changePublishedStatus(false);
        final PublishedTrip publishedTrip = new PublishedTrip(1L, LONDON_TRIP);
        publishedTrip.changeStatusToDeleted();
        given(tripRepository.findById(LONDON_TRIP.getId()))
                .willReturn(Optional.of(LONDON_TRIP));
        given(publishedTripRepository.existsByTripId(LONDON_TRIP.getId()))
                .willReturn(true);
        final PublishedStatusRequest publishedStatusRequest = new PublishedStatusRequest(true);

        // when
        tripService.updatePublishedStatus(LONDON_TRIP.getId(), publishedStatusRequest);

        // then
        assertThat(LONDON_TRIP.getPublishedStatus()).isEqualTo(PublishedStatusType.PUBLISHED);
    }

    @DisplayName("공개된 Trip을 비공개로 변경한다.")
    @Test
    void updatePublishedStatus_Unpublished() {
        // given
        LONDON_TRIP.changePublishedStatus(true);
        given(tripRepository.findById(LONDON_TRIP.getId()))
                .willReturn(Optional.of(LONDON_TRIP));
        final PublishedStatusRequest publishedStatusRequest = new PublishedStatusRequest(false);

        // when
        tripService.updatePublishedStatus(LONDON_TRIP.getId(), publishedStatusRequest);

        // then
        assertThat(LONDON_TRIP.getPublishedStatus()).isEqualTo(PublishedStatusType.UNPUBLISHED);
    }

    @Nested
    @DisplayName("Trip 일정 변경 시 DayLog 업데이트 테스트")
    class UpdateTripTests {

        TripUpdateRequest updateRequest;
        Trip trip = Trip.of(
                MEMBER,
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
                    MEMBER,
                    "파리 여행",
                    "default-image.png",
                    LocalDate.of(2023, 7, 1),
                    LocalDate.of(2023, 7, 3),
                    "",
                    null,
                    new ArrayList<>(List.of(dayLog1, dayLog2, dayLog3, extraDayLog)),
                    SharedStatusType.UNSHARED,
                    PublishedStatusType.UNPUBLISHED
            );
        }

        void changeDate(final int startDay, final int endDay) {
            updateRequest = new TripUpdateRequest(
                    "변경된 타이틀",
                    "default-image.png",
                    LocalDate.of(2023, 7, startDay),
                    LocalDate.of(2023, 7, endDay),
                    "추가된 여행 설명",
                    List.of(1L, 2L)
            );

            final Trip updatedTrip = new Trip(
                    trip.getId(),
                    MEMBER,
                    updateRequest.getImageName(),
                    updateRequest.getTitle(),
                    updateRequest.getStartDate(),
                    updateRequest.getEndDate(),
                    updateRequest.getDescription(),
                    null,
                    trip.getDayLogs(),
                    SharedStatusType.UNSHARED,
                    PublishedStatusType.UNPUBLISHED
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
                        softly.assertThat(trip.getDayLogs())
                                .usingRecursiveComparison()
                                .ignoringCollectionOrder()
                                .isEqualTo(List.of(dayLog1, dayLog2, dayLog3, extraDayLog));
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
                                            MEMBER,
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
                                            ),
                                            SharedStatusType.UNSHARED,
                                            PublishedStatusType.UNPUBLISHED
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
                        softly.assertThat(actualDayLogs)
                                .usingRecursiveComparison()
                                .ignoringCollectionOrder()
                                .isEqualTo(List.of(dayLog1, dayLog2, extraDayLog));
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
                                            MEMBER,
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
                                            ),
                                            SharedStatusType.UNSHARED,
                                            PublishedStatusType.UNPUBLISHED
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
                                .ignoringCollectionOrder()
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
