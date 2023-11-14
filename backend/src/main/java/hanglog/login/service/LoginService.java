package hanglog.login.service;

import static hanglog.global.exception.ExceptionCode.FAIL_TO_GENERATE_RANDOM_NICKNAME;
import static hanglog.global.exception.ExceptionCode.FAIL_TO_VALIDATE_TOKEN;
import static hanglog.global.exception.ExceptionCode.INVALID_REFRESH_TOKEN;

import hanglog.community.domain.repository.PublishedTripRepository;
import hanglog.global.exception.AuthException;
import hanglog.login.domain.MemberTokens;
import hanglog.login.domain.OauthProvider;
import hanglog.login.domain.OauthProviders;
import hanglog.login.domain.OauthUserInfo;
import hanglog.login.domain.RefreshToken;
import hanglog.login.domain.repository.RefreshTokenRepository;
import hanglog.login.infrastructure.BearerAuthorizationExtractor;
import hanglog.login.infrastructure.JwtProvider;
import hanglog.member.domain.Member;
import hanglog.member.domain.MemberDeleteEvent;
import hanglog.member.domain.repository.MemberRepository;
import hanglog.trip.domain.repository.CustomTripRepository;
import hanglog.trip.domain.repository.SharedTripRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private static final int MAX_TRY_COUNT = 5;
    private static final int FOUR_DIGIT_RANGE = 10000;

    private final RefreshTokenRepository refreshTokenRepository;
    private final PublishedTripRepository publishedTripRepository;
    private final MemberRepository memberRepository;
    private final CustomTripRepository customTripRepository;
    private final SharedTripRepository sharedTripRepository;
    private final OauthProviders oauthProviders;
    private final JwtProvider jwtProvider;
    private final BearerAuthorizationExtractor bearerExtractor;
    private final ApplicationEventPublisher publisher;

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
                .orElseGet(() -> createMember(socialLoginId, nickname, imageUrl));
    }

    private Member createMember(final String socialLoginId, final String nickname, final String imageUrl) {
        int tryCount = 0;
        while (tryCount < MAX_TRY_COUNT) {
            final String nicknameWithRandomNumber = nickname + generateRandomFourDigitCode();
            if (!memberRepository.existsByNickname(nicknameWithRandomNumber)) {
                return memberRepository.save(new Member(socialLoginId, nicknameWithRandomNumber, imageUrl));
            }
            tryCount += 1;
        }
        throw new AuthException(FAIL_TO_GENERATE_RANDOM_NICKNAME);
    }

    private String generateRandomFourDigitCode() {
        final int randomNumber = (int) (Math.random() * FOUR_DIGIT_RANGE);
        return String.format("%04d", randomNumber);
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

    public void deleteAccount(final Long memberId) {
        final List<Long> tripIds = customTripRepository.findTripIdsByMemberId(memberId);
        publishedTripRepository.deleteByTripIds(tripIds);
        sharedTripRepository.deleteByTripIds(tripIds);
        memberRepository.deleteByMemberId(memberId);
        publisher.publishEvent(new MemberDeleteEvent(tripIds, memberId));
    }
}
