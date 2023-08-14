package hanglog.share.service;

import static hanglog.global.exception.ExceptionCode.INVALID_SHARE_CODE;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_SHARED_CODE;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;
import static hanglog.share.fixture.ShareFixture.SHARED_TRIP;
import static hanglog.share.fixture.ShareFixture.TRIP;
import static hanglog.share.fixture.ShareFixture.UNSHARED_TRIP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import hanglog.global.exception.BadRequestException;
import hanglog.share.domain.repository.SharedTripRepository;
import hanglog.share.dto.request.SharedTripStatusRequest;
import hanglog.share.dto.response.SharedTripCodeResponse;
import hanglog.trip.domain.repository.TripRepository;
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

    @DisplayName("공유된 여행을 조회한다.")
    @Test
    void getSharedTrip() {
        // given
        given(sharedTripRepository.findBySharedCode(anyString()))
                .willReturn(Optional.of(SHARED_TRIP));

        // when
        final Long tripId = sharedTripService.getTripId("xxxxx");

        //then
        assertThat(tripId).usingRecursiveComparison()
                .isEqualTo(1L);
    }

    @DisplayName("비공유 상태의 여행 조회시 실패한다.")
    @Test
    void getSharedTrip_UnsharedFail() {
        // given
        given(sharedTripRepository.findBySharedCode(anyString()))
                .willReturn(Optional.of(UNSHARED_TRIP));

        // when & then
        assertThatThrownBy(() -> sharedTripService.getTripId("xxxxx"))
                .isInstanceOf(BadRequestException.class)
                .extracting("code")
                .isEqualTo(INVALID_SHARE_CODE.getCode());
    }

    @DisplayName("존재하지 않는 코드로 조회시 실패한다.")
    @Test
    void getSharedTrip_NoExistCode() {
        // given
        given(sharedTripRepository.findBySharedCode(anyString()))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> sharedTripService.getTripId("sharedCode"))
                .isInstanceOf(BadRequestException.class)
                .extracting("code")
                .isEqualTo(NOT_FOUND_SHARED_CODE.getCode());
    }

    @DisplayName("여행의 공유 허용상태로 변경한다.")
    @Test
    void updateSharedStatus() {
        // given
        final SharedTripStatusRequest sharedTripStatusRequest = new SharedTripStatusRequest(true);
        given(tripRepository.findById(anyLong()))
                .willReturn(Optional.of(TRIP));
        given(sharedTripRepository.findByTripId(anyLong()))
                .willReturn(Optional.of(SHARED_TRIP));

        // when
        final SharedTripCodeResponse actual = sharedTripService.updateSharedTripStatus(1L, sharedTripStatusRequest);

        //then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new SharedTripCodeResponse(SHARED_TRIP.getSharedCode()));
    }

    @DisplayName("공유 허용을 처음 할 경우 새로운 공유 code를 생성한다.")
    @Test
    void updateSharedStatus_CreateSharedTrip() {
        // given
        final SharedTripStatusRequest sharedTripStatusRequest = new SharedTripStatusRequest(true);
        given(tripRepository.findById(anyLong()))
                .willReturn(Optional.of(TRIP));
        given(sharedTripRepository.findByTripId(anyLong()))
                .willReturn(Optional.empty());

        // when
        final SharedTripCodeResponse actual = sharedTripService.updateSharedTripStatus(1L, sharedTripStatusRequest);

        //then
        assertThat(actual.getSharedCode()).isNotNull();
    }

    @DisplayName("존재하지 않는 여행의 공유 상태 변경은 예외처리한다.")
    @Test
    void updateSharedStatus_NotExistTripFail() {
        // given
        final SharedTripStatusRequest sharedTripStatusRequest = new SharedTripStatusRequest(true);
        given(tripRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> sharedTripService.updateSharedTripStatus(1L, sharedTripStatusRequest))
                .isInstanceOf(BadRequestException.class)
                .extracting("code")
                .isEqualTo(NOT_FOUND_TRIP_ID.getCode());
    }
}
