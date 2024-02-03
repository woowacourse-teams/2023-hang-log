package hanglog.admin.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordUpdateRequest {

    @NotNull(message = "기존 비밀번호를 입력해주세요.")
    @Size(min = 4, max = 20, message = "비밀번호는 4자 이상, 20자 이하여야 합니다.")
    private String currentPassword;

    @NotNull(message = "새로운 비밀번호를 입력해주세요.")
    @Size(min = 4, max = 20, message = "비밀번호는 4자 이상, 20자 이하여야 합니다.")
    private String newPassword;
}
