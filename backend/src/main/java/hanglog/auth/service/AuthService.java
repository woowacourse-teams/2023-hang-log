package hanglog.auth.service;

import static hanglog.global.exception.ExceptionCode.FAIL_TO_VALIDATE_TOKEN;
import static hanglog.global.exception.ExceptionCode.INVALID_REFRESH_TOKEN;

import hanglog.auth.domain.BearerAuthorizationExtractor;
import hanglog.auth.domain.JwtProvider;
import hanglog.auth.domain.MemberTokens;
import hanglog.auth.domain.RefreshToken;
import hanglog.auth.domain.oauthprovider.OauthProvider;
import hanglog.auth.domain.oauthprovider.OauthProviders;
import hanglog.auth.domain.oauthuserinfo.OauthUserInfo;
import hanglog.auth.domain.repository.RefreshTokenRepository;
import hanglog.global.exception.AuthException;
import hanglog.member.domain.Member;
import hanglog.member.domain.repository.MemberRepository;
import hanglog.trip.domain.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final OauthProviders oauthProviders;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TripRepository tripRepository;
    private final JwtProvider jwtProvider;
    private final BearerAuthorizationExtractor bearerExtractor;

    public MemberTokens login(final String providerName, final String code) {
        final OauthProvider provider = oauthProviders.mapping(providerName);
        final OauthUserInfo oauthUserInfo = provider.getUserInfo(code);
        final Member member = findOrCreateMember(
                oauthUserInfo.getSocialLoginId(),
                oauthUserInfo.getNickname(),
                oauthUserInfo.getImageUrl()
        );
        final MemberTokens memberTokens = jwtProvider.generateLoginToken(member.getId().toString());
        final RefreshToken savedRefreshToken = new RefreshToken(memberTokens.getRefreshToken(), member.getId());
        refreshTokenRepository.save(savedRefreshToken);
        return memberTokens;
    }

    private Member findOrCreateMember(final String socialLoginId, final String nickname, final String imageUrl) {
        return memberRepository.findBySocialLoginId(socialLoginId)
                .orElseGet(() -> memberRepository.save(new Member(socialLoginId, nickname, imageUrl)));
    }

    public String renewalAccessToken(final String refreshTokenRequest, final String authorizationHeader) {
        final String accessToken = bearerExtractor.extractAccessToken(authorizationHeader);
        if (jwtProvider.isValidRefreshAndInvalidAccess(refreshTokenRequest, accessToken)) {
            final RefreshToken refreshToken = refreshTokenRepository.findById(refreshTokenRequest)
                    .orElseThrow(() -> new AuthException(INVALID_REFRESH_TOKEN));
            return jwtProvider.regenerateAccessToken(refreshToken.getMemberId().toString());
        }
        throw new AuthException(FAIL_TO_VALIDATE_TOKEN);
    }

    public void removeMemberRefreshToken(final Long memberId) {
        refreshTokenRepository.deleteByMemberId(memberId);
    }

    public void deleteAccount(final Long memberId) {
        refreshTokenRepository.deleteByMemberId(memberId);
        tripRepository.deleteAllByMemberId(memberId);
        memberRepository.deleteById(memberId);
    }
}
