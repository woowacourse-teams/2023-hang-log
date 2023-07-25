package hanglog.trip.dto.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemsOrdinalUpdateRequest {
    @NotEmpty(message = "아이템 아이디들을 입력해주세요.")
    private List<Long> itemIds;
}
