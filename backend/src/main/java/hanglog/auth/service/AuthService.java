package hanglog.auth.service;

import static hanglog.global.exception.ExceptionCode.FAIL_TO_GENERATE_RANDOM_NICKNAME;
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
import hanglog.trip.domain.repository.CustomDayLogRepository;
import hanglog.trip.domain.repository.CustomItemRepository;
import hanglog.trip.domain.repository.CustomTripRepository;
import hanglog.trip.domain.repository.TripRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private static final int MAX_TRY_COUNT = 5;
    private static final int FOUR_DIGIT_RANGE = 10000;

    private final MemberRepository memberRepository;
    private final OauthProviders oauthProviders;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TripRepository tripRepository;
    private final JwtProvider jwtProvider;
    private final BearerAuthorizationExtractor bearerExtractor;

    private final CustomTripRepository customTripRepository;
    private final CustomDayLogRepository customDayLogRepository;
    private final CustomItemRepository customItemRepository;

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

    public String generateRandomFourDigitCode() {
        final int randomNumber = (int) (Math.random() * FOUR_DIGIT_RANGE);
        return String.format("%04d", randomNumber);
    }

    public String renewalAccessToken(final String refreshTokenRequest, final String authorizationHeader) {
        final String accessToken = bearerExtractor.extractAccessToken(authorizationHeader);
        if (jwtProvider.isValidRefreshAndInvalidAccess(refreshTokenRequest, accessToken)) {
            final RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenRequest)
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
        final List<Long> dayLogIds = customDayLogRepository.findDayLogIdsByTripIds(tripIds);
        final List<Long> itemIds = customItemRepository.findItemIdsByDayLogIds(dayLogIds);


        refreshTokenRepository.deleteByMemberId(memberId);
        tripRepository.deleteAllByMemberId(memberId);
        memberRepository.deleteById(memberId);
    }
}
