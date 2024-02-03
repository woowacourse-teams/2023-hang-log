package hanglog.admin.service;

import static hanglog.global.exception.ExceptionCode.FAIL_TO_VALIDATE_TOKEN;
import static hanglog.global.exception.ExceptionCode.INVALID_PASSWORD;
import static hanglog.global.exception.ExceptionCode.INVALID_REFRESH_TOKEN;
import static hanglog.global.exception.ExceptionCode.INVALID_USER_NAME;

import hanglog.admin.domain.AdminMember;
import hanglog.admin.domain.repository.AdminMemberRepository;
import hanglog.admin.dto.request.AdminLoginRequest;
import hanglog.admin.infrastructure.PasswordEncoder;
import hanglog.global.exception.AdminException;
import hanglog.global.exception.AuthException;
import hanglog.login.domain.MemberTokens;
import hanglog.login.domain.RefreshToken;
import hanglog.login.domain.repository.RefreshTokenRepository;
import hanglog.login.infrastructure.BearerAuthorizationExtractor;
import hanglog.login.infrastructure.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminLoginService {

    private final AdminMemberRepository adminMemberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final BearerAuthorizationExtractor bearerExtractor;
    private final PasswordEncoder passwordEncoder;

    public MemberTokens login(final AdminLoginRequest adminLoginRequest) {
        final AdminMember adminMember = adminMemberRepository
                .findByUsername(adminLoginRequest.getUsername())
                .orElseThrow(() -> new AdminException(INVALID_USER_NAME));

        if (passwordEncoder.matches(adminLoginRequest.getPassword(), adminMember.getPassword())) {
            final MemberTokens memberTokens = jwtProvider.generateLoginToken(adminMember.getId().toString());
            final RefreshToken savedRefreshToken = new RefreshToken(memberTokens.getRefreshToken(),
                    adminMember.getId());
            refreshTokenRepository.save(savedRefreshToken);
            return memberTokens;
        }

        throw new AdminException(INVALID_PASSWORD);
    }

    public String renewalAccessToken(final String refreshTokenRequest, final String authorizationHeader) {
        final String accessToken = bearerExtractor.extractAccessToken(authorizationHeader);
        if (jwtProvider.isValidRefreshAndInvalidAccess(refreshTokenRequest, accessToken)) {
            final RefreshToken refreshToken = refreshTokenRepository.findById(refreshTokenRequest)
                    .orElseThrow(() -> new AuthException(INVALID_REFRESH_TOKEN));
            return jwtProvider.regenerateAccessToken(refreshToken.getMemberId().toString());
        }
        if (jwtProvider.isValidRefreshAndValidAccess(refreshTokenRequest, accessToken)) {
            return accessToken;
        }
        throw new AuthException(FAIL_TO_VALIDATE_TOKEN);
    }

    public void removeRefreshToken(final String refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
    }
}
