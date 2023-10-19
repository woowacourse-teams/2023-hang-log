package hanglog.trip.domain;

import hanglog.expense.domain.Amount;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DayLogExpense {

    private final DayLog dayLog;
    private final Amount amount;
}
