package hanglog.trip.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpenseRequest {

    private String currency;
    private Integer amount;
    private String category;
}
