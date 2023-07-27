package hanglog.expense.dto.response;

import hanglog.trip.domain.TripCity;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CitiesInExpenseResponse {

    private final long id;
    private final String name;

    public static List<CitiesInExpenseResponse> of(final List<TripCity> cities) {
        return cities.stream().map(city ->
                new CitiesInExpenseResponse(
                        city.getCity().getId(),
                        city.getCity().getName()
                )
        ).toList();
    }
}
