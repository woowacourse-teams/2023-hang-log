package hanglog.auth.domain;

import static hanglog.global.exception.ExceptionCode.INVALID_ACCESS_TOKEN;

import hanglog.global.exception.InvalidJwtException;

public class BearerAuthorizationExtractor {

    private static final String BEARER_TYPE = "Bearer ";

    public String extractAccessToken(String header) {
        if (header != null && header.startsWith(BEARER_TYPE)) {
            return header.substring(BEARER_TYPE.length()).trim();
        }
        throw new InvalidJwtException(INVALID_ACCESS_TOKEN);
    }
}
