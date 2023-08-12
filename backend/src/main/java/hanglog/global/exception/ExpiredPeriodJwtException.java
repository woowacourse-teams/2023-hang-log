package hanglog.global.exception;

import lombok.Getter;

@Getter
public class ExpiredPeriodJwtException extends AuthException {

    public ExpiredPeriodJwtException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
