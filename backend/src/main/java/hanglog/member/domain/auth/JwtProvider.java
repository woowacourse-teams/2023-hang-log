package hanglog.member.domain.auth;

import static hanglog.global.exception.ExceptionCode.EXPIRED_PERIOD_ACCESS_TOKEN;
import static hanglog.global.exception.ExceptionCode.EXPIRED_PERIOD_REFRESH_TOKEN;
import static hanglog.global.exception.ExceptionCode.INVALID_ACCESS_TOKEN;
import static hanglog.global.exception.ExceptionCode.INVALID_REFRESH_TOKEN;

import hanglog.global.exception.AuthException;
import hanglog.member.dto.MemberTokens;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    public static final String EMPTY_SUBJECT = "";

    private final SecretKey secretKey;
    private final Long accessExpirationTime;
    private final Long refreshExpirationTime;

    public JwtProvider(
            @Value("${security.jwt.secret-key}") final String secretKey,
            @Value("${security.jwt.access-expiration-time}") final Long accessExpirationTime,
            @Value("${security.jwt.refresh-expiration-time}") final Long refreshExpirationTime
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessExpirationTime = accessExpirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
    }

    public MemberTokens generateLoginToken(final String subject) {
        final String refreshToken = createToken(EMPTY_SUBJECT, refreshExpirationTime);
        final String accessToken = createToken(subject, accessExpirationTime);
        return new MemberTokens(refreshToken, accessToken);
    }

    private String createToken(final String subject, final Long validityInMilliseconds) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public void validateToken(final MemberTokens memberTokens) {
        validateRefreshToken(memberTokens);
        validateAccessToken(memberTokens);
    }

    private void validateRefreshToken(final MemberTokens memberTokens) {
        try {
            parseToken(memberTokens.getRefreshToken());
        } catch (ExpiredJwtException e) {
            throw new AuthException(EXPIRED_PERIOD_REFRESH_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthException(INVALID_REFRESH_TOKEN);
        }
    }

    private void validateAccessToken(final MemberTokens memberTokens) {
        try {
            parseToken(memberTokens.getAccessToken());
        } catch (ExpiredJwtException e) {
            throw new AuthException(EXPIRED_PERIOD_ACCESS_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthException(INVALID_ACCESS_TOKEN);
        }
    }

    private void parseToken(final String token) {
        Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }

    public String getSubject(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
