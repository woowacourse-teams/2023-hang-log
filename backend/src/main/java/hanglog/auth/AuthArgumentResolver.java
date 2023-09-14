package hanglog.auth;

import static hanglog.global.exception.ExceptionCode.INVALID_REQUEST;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_REFRESH_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import hanglog.auth.domain.Accessor;
import hanglog.auth.domain.BearerAuthorizationExtractor;
import hanglog.auth.domain.JwtProvider;
import hanglog.auth.domain.MemberTokens;
import hanglog.auth.domain.repository.RefreshTokenRepository;
import hanglog.global.exception.BadRequestException;
import hanglog.global.exception.RefreshTokenException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String REFRESH_TOKEN = "refresh-token";

    private final JwtProvider jwtProvider;

    private final BearerAuthorizationExtractor extractor;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.withContainingClass(Long.class)
                .hasParameterAnnotation(Auth.class);
    }

    @Override
    public Accessor resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) {
        final HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            throw new BadRequestException(INVALID_REQUEST);
        }

        try {
            final String refreshToken = extractRefreshToken(request.getCookies());
            final String accessToken = extractor.extractAccessToken(webRequest.getHeader(AUTHORIZATION));
            jwtProvider.validateTokens(new MemberTokens(refreshToken, accessToken));

            final Long memberId = Long.valueOf(jwtProvider.getSubject(accessToken));
            return Accessor.member(memberId);
        } catch (final RefreshTokenException e) {
            return Accessor.guest();
        }
    }

    private String extractRefreshToken(final Cookie... cookies) {
        if (cookies == null) {
            throw new RefreshTokenException(NOT_FOUND_REFRESH_TOKEN);
        }
        return Arrays.stream(cookies)
                .filter(this::isValidRefreshToken)
                .findFirst()
                .orElseThrow(() -> new RefreshTokenException(NOT_FOUND_REFRESH_TOKEN))
                .getValue();
    }

    private boolean isValidRefreshToken(final Cookie cookie) {
        // TODO: refreshToken 만료 기한 검사 필요
        return REFRESH_TOKEN.equals(cookie.getName()) &&
                refreshTokenRepository.existsByToken(cookie.getValue());
    }
}
