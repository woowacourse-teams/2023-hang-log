package hanglog.expense.domain;

import hanglog.trip.domain.DayLog;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DayLogExpense {

    private final DayLog dayLog;
    private final Amount amount;
}
