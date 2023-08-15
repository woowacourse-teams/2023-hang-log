package hanglog.auth;

import static hanglog.global.exception.ExceptionCode.NULL_REFRESH_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import hanglog.auth.domain.BearerAuthorizationExtractor;
import hanglog.auth.domain.JwtProvider;
import hanglog.auth.domain.MemberTokens;
import hanglog.global.exception.AuthException;
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

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.withContainingClass(Long.class)
                .hasParameterAnnotation(Auth.class);
    }

    @Override
    public Long resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) throws Exception {
        final HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        final String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> REFRESH_TOKEN.equals(cookie.getName()))
                .findFirst()
                .orElseThrow(() -> new AuthException(NULL_REFRESH_TOKEN))
                .getValue();

        final String accessToken = extractor.extractAccessToken(webRequest.getHeader(AUTHORIZATION));

        jwtProvider.validateTokens(new MemberTokens(refreshToken, accessToken));
        return Long.valueOf(jwtProvider.getSubject(accessToken));
    }
}
