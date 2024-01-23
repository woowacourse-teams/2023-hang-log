package hanglog.integration.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import hanglog.admin.dto.request.AdminLoginRequest;
import hanglog.admin.dto.request.AdminMemberCreateRequest;
import hanglog.admin.service.AdminLoginService;
import hanglog.admin.service.AdminMemberService;
import hanglog.global.exception.AdminException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminLoginServiceIntegrationTest extends RedisServiceIntegrationTest {

    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private AdminMemberService adminMemberService;

    @DisplayName("계정 정보가 올바르면 로그인에 성공한다.")
    @Test
    public void login() {
        // given
        final String username = "username";
        final String pw = "password";
        adminMemberService.createAdminMember(new AdminMemberCreateRequest(username, pw, "ADMIN"));

        // when & then
        assertDoesNotThrow(() -> adminLoginService.login(new AdminLoginRequest(username, pw)));
    }

    @DisplayName("비밀번호가 틀리면 로그인에 실패한다.")
    @Test
    public void login_fail() {
        // given
        final String username = "username";
        final String pw = "password";
        adminMemberService.createAdminMember(new AdminMemberCreateRequest(username, pw, "ADMIN"));

        // when & then
        assertThatThrownBy(() -> adminLoginService.login(new AdminLoginRequest(username, "wrongPassword")))
                .isInstanceOf(AdminException.class);
    }
}
