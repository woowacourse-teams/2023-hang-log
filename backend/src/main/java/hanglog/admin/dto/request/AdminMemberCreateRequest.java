package hanglog.admin.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminMemberCreateRequest {

    @NotNull(message = "사용자 이름을 입력해주세요.")
    @Size(max = 20, message = "사용자 이름은 20자를 초과할 수 없습니다.")
    private String userName;

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Size(min = 4, max = 20, message = "비밀번호는 4자 이상, 20자 이하여야 합니다.")
    private String password;

    @NotNull(message = "관리자 권한을 선택해 주세요.")
    private String adminType;
}
