package hanglog.trip.dto;

import hanglog.city.domain.City;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TripCityElement {

    private final Long tripId;
    private final City city;
}
