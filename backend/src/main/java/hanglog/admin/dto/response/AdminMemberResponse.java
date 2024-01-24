package hanglog.admin.dto.response;

import static lombok.AccessLevel.PRIVATE;

import hanglog.admin.domain.AdminMember;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class AdminMemberResponse {

    private final Long id;
    private final String userName;
    private final String adminType;

    public static AdminMemberResponse from(final AdminMember adminMember) {
        return new AdminMemberResponse(
                adminMember.getId(),
                adminMember.getUsername(),
                adminMember.getAdminType().name()
        );
    }
}
