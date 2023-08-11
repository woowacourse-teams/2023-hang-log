package hanglog.share.dto.response;

import static hanglog.share.domain.type.SharedStatusType.UNSHARED;

import hanglog.share.domain.SharedTrip;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SharedTripCodeResponse {

    private final String sharedCode;

    public static SharedTripCodeResponse of(final SharedTrip sharedTrip) {
        if (sharedTrip.getSharedStatus() == UNSHARED) {
            return new SharedTripCodeResponse(null);
        }
        return new SharedTripCodeResponse(sharedTrip.getShareCode());
    }
}
