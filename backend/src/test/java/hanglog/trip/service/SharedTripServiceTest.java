package hanglog.trip.service;

import static hanglog.global.exception.ExceptionCode.INVALID_SHARE_CODE;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_SHARED_CODE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import hanglog.global.exception.BadRequestException;
import hanglog.trip.domain.repository.SharedTripRepository;
import hanglog.trip.fixture.ShareFixture;
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

    @DisplayName("공유된 여행을 조회한다.")
    @Test
    void getSharedTrip() {
        // given
        given(sharedTripRepository.findBySharedCode(anyString()))
                .willReturn(Optional.of(ShareFixture.SHARED_TRIP));

        // when
        final Long tripId = sharedTripService.getTripId("sharedCode");

        //then
        assertThat(tripId).usingRecursiveComparison()
                .isEqualTo(1L);
    }

    @DisplayName("비공유 상태의 여행 조회시 실패한다.")
    @Test
    void getSharedTrip_UnsharedFail() {
        // given
        given(sharedTripRepository.findBySharedCode(anyString()))
                .willReturn(Optional.of(ShareFixture.UNSHARED_TRIP));

        // when & then
        assertThatThrownBy(() -> sharedTripService.getTripId("sharedCode"))
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
        assertThatThrownBy(() -> sharedTripService.getTripId("noExistSharedCode"))
                .isInstanceOf(BadRequestException.class)
                .extracting("code")
                .isEqualTo(NOT_FOUND_SHARED_CODE.getCode());
    }
}
