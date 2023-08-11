package hanglog.member.service;

import hanglog.member.domain.Member;
import hanglog.member.domain.auth.JwtProvider;
import hanglog.member.domain.auth.OauthProvider;
import hanglog.member.domain.auth.OauthProviders;
import hanglog.member.domain.auth.UserInfo;
import hanglog.member.domain.repository.MemberRepository;
import hanglog.member.domain.MemberTokens;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final OauthProviders oauthProviders;
    private final JwtProvider jwtProvider;

    public MemberTokens login(final String providerName, final String code) {
        final OauthProvider provider = oauthProviders.mapping(providerName);
        final UserInfo userInfo = provider.getUserInfo(code);
        final Member member = findOrCreateMember(userInfo.getId(), userInfo.getNickname(), userInfo.getImageUrl());
        final MemberTokens memberTokens = jwtProvider.generateLoginToken(member.getId().toString());
        member.assignRefreshToken(memberTokens.getRefreshToken());
        return memberTokens;
    }

    private Member findOrCreateMember(final String socialLoginId, final String nickname, final String imageUrl) {
        return memberRepository.findBySocialLoginId(socialLoginId)
                .orElseGet(() -> memberRepository.save(new Member(socialLoginId, nickname, imageUrl)));
    }

    public MemberTokens renewalAccessToken(final String refreshToken, final String accessToken) {
        return jwtProvider.regenerateAccessToken(refreshToken, accessToken);
    }

    public void removeMemberRefreshToken(final String refreshToken, final String accessToken) {
        jwtProvider.validateTokens(new MemberTokens(refreshToken, accessToken));
        final Long memberId = Long.valueOf(jwtProvider.getSubject(accessToken));
        final Member member = memberRepository.findById(memberId).orElseThrow();
        member.removeRefreshToken();
        memberRepository.save(member);
    }
}
