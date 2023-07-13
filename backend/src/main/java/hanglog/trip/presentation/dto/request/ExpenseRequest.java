package hanglog.trip.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpenseRequest {

    private final String currency;
    private final Integer amount;
    private final Long categoryId;
}
