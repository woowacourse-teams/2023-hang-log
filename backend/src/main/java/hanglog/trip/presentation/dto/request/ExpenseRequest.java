package hanglog.trip.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpenseRequest {

    @NotNull(message = "화폐의 통화명 입력해주세요.")
    @Size(max = 45, message = "화폐 통화명은 45자를 초과할 수 없습니다.")
    private final String currency;

    @NotNull(message = "화폐의 금액을 입력해주세요.")
    private final Integer amount;

    @NotNull(message = "화폐의 카테고리를 입력해주세요.")
    private final Long categoryId;
}
