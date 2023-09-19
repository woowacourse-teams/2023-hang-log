package hanglog.trip.dto;

import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
public class PublishedStatusRequest {

    @NotNull(message = "커뮤니티 공개 상태를 선택해주세요.")
    private Boolean publishedStatus;
}
