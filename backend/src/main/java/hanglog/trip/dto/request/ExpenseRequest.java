package hanglog.trip.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpenseRequest {

    @NotBlank(message = "화폐의 통화명을 입력해주세요.")
    @Size(max = 45, message = "화폐 통화명은 45자를 초과할 수 없습니다.")
    private final String currency;

    @NotNull(message = "화폐의 금액을 입력해주세요.")
    @DecimalMin(value = "0.0", message = "화폐의 금액은 0원이상이어야 합니다.")
    private final Double amount;

    @NotNull(message = "화폐의 카테고리를 입력해주세요.")
    private final Long categoryId;
}
