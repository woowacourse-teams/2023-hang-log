package hanglog.member.service;

import hanglog.member.domain.Member;
import hanglog.member.domain.auth.JwtProvider;
import hanglog.member.domain.auth.OauthProvider;
import hanglog.member.domain.auth.OauthProviders;
import hanglog.member.domain.auth.UserInfo;
import hanglog.member.domain.repository.MemberRepository;
import hanglog.member.dto.TokenResponse;
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

    public TokenResponse login(final String providerName, final String code) {
        final OauthProvider provider = oauthProviders.mapping(providerName);
        final UserInfo userInfo = provider.getUserInfo(code);
        final Member member = save(userInfo.getId(), userInfo.getNickname(), userInfo.getImageUrl());
        final String accessToken = jwtProvider.createToken(member.getId());
        return new TokenResponse(accessToken);
    }

    private Member save(final String socialLoginId, final String nickname, final String imageUrl) {
        final Member member = new Member(socialLoginId, nickname, imageUrl);
        return memberRepository.findBySocialLoginId(socialLoginId)
                .orElseGet(() -> memberRepository.save(member));
    }
}
