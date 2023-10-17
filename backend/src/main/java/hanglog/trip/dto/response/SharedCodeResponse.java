package hanglog.trip.dto.response;

import hanglog.trip.domain.SharedTrip;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SharedCodeResponse {

    private final String sharedCode;

    public static SharedCodeResponse of(final SharedTrip sharedTrip) {
        if (!sharedTrip.isShared()) {
            return new SharedCodeResponse(null);
        }
        return new SharedCodeResponse(sharedTrip.getSharedCode());
    }
}
