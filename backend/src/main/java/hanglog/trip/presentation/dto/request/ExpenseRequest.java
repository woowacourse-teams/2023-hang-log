package hanglog.trip.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpenseRequest {

    private String currency;
    private Integer amount;
    private String category;
}
