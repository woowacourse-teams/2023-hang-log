package hanglog.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import hanglog.admin.dto.request.AdminMemberCreateRequest;
import hanglog.admin.dto.request.PasswordUpdateRequest;
import hanglog.admin.dto.response.AdminMemberResponse;
import hanglog.admin.service.AdminMemberService;
import hanglog.global.config.SecurityTestConfig;
import hanglog.global.exception.AdminException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql(value = {"classpath:data/truncate.sql"})
@Import({
        AdminMemberService.class,
        SecurityTestConfig.class
})
public class AdminMemberServiceIntegrationTest {
    private static final AdminMemberCreateRequest CREATE_REQUEST = new AdminMemberCreateRequest(
            "username",
            "password",
            "ADMIN"
    );

    @Autowired
    private AdminMemberService adminMemberService;


    @DisplayName("관리자 계정을 생성한다.")
    @Test
    void createAdminMember() {
        // when & then
        assertThat(adminMemberService.createAdminMember(CREATE_REQUEST)).isPositive();
    }

    @DisplayName("관리자 계정 목록을 조회한다.")
    @Test
    void getAdminMembers() {
        // given
        adminMemberService.createAdminMember(CREATE_REQUEST);

        // when
        final List<AdminMemberResponse> actual = adminMemberService.getAdminMembers();

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual).hasSize(1);
            softly.assertThat(actual.get(0).getUsername()).isEqualTo(CREATE_REQUEST.getUsername());
        });
    }

    @DisplayName("관리자 계정의 비밀번호를 변경한다.")
    @Test
    void updatePassword() {
        // given
        final Long id = adminMemberService.createAdminMember(CREATE_REQUEST);
        final PasswordUpdateRequest request = new PasswordUpdateRequest(CREATE_REQUEST.getPassword(), "newPassword");

        // when & then
        assertDoesNotThrow(() -> adminMemberService.updatePassword(id, request));
    }

    @DisplayName("관리자 계정의 기존 비밀번호를 틀리변 비밀번호를 변경할 수 없다.")
    @Test
    void updatePassword_Fail() {
        // given
        final Long id = adminMemberService.createAdminMember(CREATE_REQUEST);
        final PasswordUpdateRequest request = new PasswordUpdateRequest("wrongPassword", "newPassword");

        // when & then
        assertThatThrownBy(() -> adminMemberService.updatePassword(id, request))
                .isInstanceOf(AdminException.class);
    }
}
