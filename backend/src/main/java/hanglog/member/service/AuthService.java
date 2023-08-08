package hanglog.member.service;

import hanglog.member.domain.Member;
import hanglog.member.domain.auth.Provider;
import hanglog.member.domain.auth.Providers;
import hanglog.member.domain.auth.UserInfo;
import hanglog.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;
    private final Providers providers;

    public AuthService(final MemberRepository memberRepository, final Providers providers) {
        this.memberRepository = memberRepository;
        this.providers = providers;
    }

    public Long login(final String providerName, final String code) {
        final Provider provider = providers.mapping(providerName);
        final UserInfo userInfo = provider.getUserInfo(code);
        final Member member = save(userInfo.getId(), userInfo.getNickname(), userInfo.getImageUrl());
        return member.getId();
    }

    // TODO : 토큰 받기
    private Member save(final String socialLoginId, final String nickname, final String imageUrl) {
        final Member member = new Member(socialLoginId, nickname, imageUrl);
        return memberRepository.findBySocialLoginId(socialLoginId)
                .orElseGet(() -> memberRepository.save(member));
    }
}
