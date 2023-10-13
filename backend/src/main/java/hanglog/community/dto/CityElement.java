package hanglog.community.dto;

import hanglog.city.domain.City;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CityElement {

    private final Long tripId;
    private final City city;
}
