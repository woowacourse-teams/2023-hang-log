package hanglog.trip.presentation.dto.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceRequest {

    private final String apiId;
    private final String name;
    private final String address;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final String categoryApiId;
}
