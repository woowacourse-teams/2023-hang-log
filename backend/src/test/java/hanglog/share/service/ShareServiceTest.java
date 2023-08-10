package hanglog.share.service;

import static hanglog.share.domain.type.SharedStatusType.SHARED;
import static hanglog.trip.fixture.CityFixture.LONDON;
import static hanglog.trip.fixture.CityFixture.PARIS;
import static hanglog.trip.fixture.TripFixture.LONDON_TRIP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import hanglog.share.domain.SharedTrip;
import hanglog.share.domain.repository.SharedTripRepository;
import hanglog.share.dto.request.TripSharedStatusRequest;
import hanglog.share.dto.response.TripSharedCodeResponse;
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
class ShareServiceTest {

    @InjectMocks
    private ShareService shareService;

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
        final SharedTrip sharedTrip = new SharedTrip(1L, LONDON_TRIP, "xxxxx", SHARED);
        given(tripRepository.findById(1L))
                .willReturn(Optional.of(LONDON_TRIP));

        given(tripCityRepository.findByTripId(1L))
                .willReturn(List.of(new TripCity(LONDON_TRIP, PARIS), new TripCity(LONDON_TRIP, LONDON)));
        given(sharedTripRepository.findByShareCode(anyString()))
                .willReturn(Optional.of(sharedTrip));

        // when
        final TripDetailResponse actual = shareService.getTripDetail("xxxxx");

        //then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(TripDetailResponse.of(LONDON_TRIP, List.of(PARIS, LONDON)));
    }

    @DisplayName("여행의 공유 허용상태로 변경한다.")
    @Test
    void updateSharedStatus() {
        // given
        final SharedTrip sharedTrip = new SharedTrip(1L, LONDON_TRIP, "xxxxx", SHARED);
        final TripSharedStatusRequest tripSharedStatusRequest = new TripSharedStatusRequest(true);
        given(tripRepository.findById(anyLong()))
                .willReturn(Optional.of(LONDON_TRIP));
        given(sharedTripRepository.findByTripId(anyLong()))
                .willReturn(Optional.of(sharedTrip));

        // when
        final TripSharedCodeResponse actual = shareService.updateSharedStatus(1L, tripSharedStatusRequest);

        //then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new TripSharedCodeResponse(sharedTrip.getShareCode()));
    }
}
