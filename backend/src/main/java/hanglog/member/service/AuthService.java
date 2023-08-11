package hanglog.member.service;

import hanglog.member.domain.MemberTokens;
import hanglog.member.domain.auth.JwtProvider;
import hanglog.member.domain.auth.OauthProvider;
import hanglog.member.domain.auth.OauthProviders;
import hanglog.member.domain.auth.UserInfo;
import hanglog.member.domain.entity.Member;
import hanglog.member.domain.entity.RefreshToken;
import hanglog.member.domain.repository.MemberRepository;
import hanglog.member.domain.repository.RefreshTokenRepository;
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
    private final JwtProvider jwtProvider;

    public MemberTokens login(final String providerName, final String code) {
        final OauthProvider provider = oauthProviders.mapping(providerName);
        final UserInfo userInfo = provider.getUserInfo(code);
        final Member member = findOrCreateMember(
                userInfo.getSocialLoginId(),
                userInfo.getNickname(),
                userInfo.getImageUrl()
        );
        final MemberTokens memberTokens = jwtProvider.generateLoginToken(member.getId().toString());
        final RefreshToken savedRefreshToken = new RefreshToken(member, memberTokens.getRefreshToken());
        refreshTokenRepository.save(savedRefreshToken);
        return memberTokens;
    }

    private Member findOrCreateMember(final String socialLoginId, final String nickname, final String imageUrl) {
        return memberRepository.findBySocialLoginId(socialLoginId)
                .orElseGet(() -> memberRepository.save(new Member(socialLoginId, nickname, imageUrl)));
    }

    public String renewalAccessToken(final String refreshToken, final String accessToken) {
        final MemberTokens memberTokens = jwtProvider.regenerateAccessToken(refreshToken, accessToken);
        return memberTokens.getAccessToken();
    }

    public void removeMemberRefreshToken(final String refreshToken, final String accessToken) {
        jwtProvider.validateTokens(new MemberTokens(refreshToken, accessToken));
        final Long memberId = Long.valueOf(jwtProvider.getSubject(accessToken));
        refreshTokenRepository.deleteByMemberId(memberId);
    }
}
