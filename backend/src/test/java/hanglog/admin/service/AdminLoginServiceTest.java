package hanglog.admin.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import hanglog.admin.domain.AdminMember;
import hanglog.admin.domain.repository.AdminMemberRepository;
import hanglog.admin.domain.type.AdminType;
import hanglog.admin.dto.request.AdminLoginRequest;
import hanglog.admin.infrastructure.PasswordEncoder;
import hanglog.global.exception.AdminException;
import hanglog.login.domain.MemberTokens;
import hanglog.login.domain.repository.RefreshTokenRepository;
import hanglog.login.infrastructure.BearerAuthorizationExtractor;
import hanglog.login.infrastructure.JwtProvider;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdminLoginServiceTest {
    @InjectMocks
    private AdminLoginService adminLoginService;

    @Mock
    private AdminMemberRepository adminMemberRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private BearerAuthorizationExtractor bearerExtractor;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    public void testLoginSuccess() {
        // given
        final AdminLoginRequest loginRequest = new AdminLoginRequest("user", "password");
        final AdminMember adminMember = new AdminMember(1L, "user", "password", AdminType.ADMIN);
        final MemberTokens memberTokens = new MemberTokens("accessToken", "refreshToken");

        when(adminMemberRepository.findByUserName(anyString())).thenReturn(Optional.of(adminMember));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtProvider.generateLoginToken(anyString())).thenReturn(memberTokens);

        // when
        final MemberTokens result = adminLoginService.login(loginRequest);

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(memberTokens.getAccessToken()).isEqualTo(result.getAccessToken());
            softly.assertThat(memberTokens.getRefreshToken()).isEqualTo(result.getRefreshToken());
        });
    }

    @Test
    public void testLoginFailure_InvalidPassword() {
        // given
        final AdminLoginRequest loginRequest = new AdminLoginRequest("user", "wrongpassword");
        final AdminMember adminMember = new AdminMember("user", "password", AdminType.ADMIN);

        when(adminMemberRepository.findByUserName(anyString())).thenReturn(Optional.of(adminMember));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // when & then
        assertThrows(AdminException.class, () -> adminLoginService.login(loginRequest));
    }
}
