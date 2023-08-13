package hanglog.share.service;

import static hanglog.share.domain.type.SharedStatusType.SHARED;
import static hanglog.share.domain.type.SharedStatusType.UNSHARED;
import static hanglog.share.fixture.ShareFixture.BEIJING;
import static hanglog.share.fixture.ShareFixture.CALIFORNIA;
import static hanglog.share.fixture.ShareFixture.TOKYO;
import static hanglog.share.fixture.ShareFixture.TRIP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import hanglog.global.exception.BadRequestException;
import hanglog.share.domain.SharedTrip;
import hanglog.share.domain.repository.SharedTripRepository;
import hanglog.share.dto.request.SharedTripStatusRequest;
import hanglog.share.dto.response.SharedTripCodeResponse;
import hanglog.trip.domain.TripCity;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.response.TripDetailResponse;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class SharedTripServiceTest {

    @InjectMocks
    private SharedTripService sharedTripService;

    @Mock
    private SharedTripRepository sharedTripRepository;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private TripCityRepository tripCityRepository;

    @DisplayName("공유된 여행을 조회한다.")
    @Test
    void getSharedTrip() {
        // given
        final SharedTrip sharedTrip = new SharedTrip(1L, TRIP, "xxxxx", SHARED);
        given(tripRepository.findById(1L))
                .willReturn(Optional.of(TRIP));
        given(tripCityRepository.findByTripId(1L))
                .willReturn(List.of(new TripCity(TRIP, CALIFORNIA), new TripCity(TRIP, TOKYO),
                        new TripCity(TRIP, BEIJING)));
        given(sharedTripRepository.findBySharedCode(anyString()))
                .willReturn(Optional.of(sharedTrip));

        // when
        final TripDetailResponse actual = sharedTripService.getTripDetail("xxxxx");

        //then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(TripDetailResponse.of(TRIP, List.of(CALIFORNIA, TOKYO, BEIJING)));
    }

    @DisplayName("비공유 상태의 여행 조회시 실패한다.")
    @Test
    void getSharedTrip_UnsharedFail() {
        // given
        final SharedTrip sharedTrip = new SharedTrip(1L, TRIP, "xxxxx", UNSHARED);

        given(sharedTripRepository.findBySharedCode(anyString()))
                .willReturn(Optional.of(sharedTrip));

        // when & then
        assertThatThrownBy(() -> sharedTripService.getTripDetail("xxxxx"))
                .isInstanceOf(BadRequestException.class)
                .extracting("code")
                .isEqualTo(7002);
    }

    @DisplayName("존재하지 않는 코드로 조회시 실패한다.")
    @Test
    void getSharedTrip_NoExistCode() {
        // given
        given(sharedTripRepository.findBySharedCode(anyString()))
                .willReturn(Optional.ofNullable(null));

        // when & then
        assertThatThrownBy(() -> sharedTripService.getTripDetail("xxxxx"))
                .isInstanceOf(BadRequestException.class)
                .extracting("code")
                .isEqualTo(7001);
    }

    @DisplayName("여행의 공유 허용상태로 변경한다.")
    @Test
    void updateSharedStatus() {
        // given
        final SharedTrip sharedTrip = new SharedTrip(1L, TRIP, "xxxxx", SHARED);
        final SharedTripStatusRequest sharedTripStatusRequest = new SharedTripStatusRequest(true);
        given(tripRepository.findById(anyLong()))
                .willReturn(Optional.of(TRIP));
        given(sharedTripRepository.findByTripId(anyLong()))
                .willReturn(Optional.of(sharedTrip));

        // when
        final SharedTripCodeResponse actual = sharedTripService.updateSharedStatus(1L, sharedTripStatusRequest);

        //then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new SharedTripCodeResponse(sharedTrip.getSharedCode()));
    }

    @DisplayName("공유 허용을 처음 할 경우 새로운 공유 code를 생성한다.")
    @Test
    void updateSharedStatus_CreateSharedTrip() {
        // given
        final SharedTripStatusRequest sharedTripStatusRequest = new SharedTripStatusRequest(true);
        given(tripRepository.findById(anyLong()))
                .willReturn(Optional.of(TRIP));
        given(sharedTripRepository.findByTripId(anyLong()))
                .willReturn(Optional.ofNullable(null));

        // when
        final SharedTripCodeResponse actual = sharedTripService.updateSharedStatus(1L, sharedTripStatusRequest);

        //then
        assertThat(actual.getSharedCode()).isNotNull();
    }

    @DisplayName("존재하지 않는 여행의 공유 상태 변경은 예외처리한다.")
    @Test
    void updateSharedStatus_NotExistTripFail() {
        // given
        final SharedTripStatusRequest sharedTripStatusRequest = new SharedTripStatusRequest(true);
        given(tripRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(null));

        // when & then
        assertThatThrownBy(() -> sharedTripService.updateSharedStatus(1L, sharedTripStatusRequest))
                .isInstanceOf(BadRequestException.class)
                .extracting("code")
                .isEqualTo(1001);
    }
}
