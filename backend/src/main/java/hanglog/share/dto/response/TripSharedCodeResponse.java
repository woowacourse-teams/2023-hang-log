package hanglog.share.dto.response;

import static hanglog.share.domain.type.SharedStatusType.UNSHARED;

import hanglog.share.domain.SharedTrip;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TripSharedCodeResponse {

    private final String sharedCode;

    public static TripSharedCodeResponse of(final SharedTrip sharedTrip) {
        if (sharedTrip.getSharedStatus() == UNSHARED) {
            return new TripSharedCodeResponse(null);
        }
        return new TripSharedCodeResponse(sharedTrip.getShareCode());
    }
}
