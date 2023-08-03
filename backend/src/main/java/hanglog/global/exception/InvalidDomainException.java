package hanglog.global.exception;

import lombok.Getter;

@Getter
public class InvalidDomainException extends RuntimeException {

    private final int code;
    private final String message;

    public InvalidDomainException(final ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}
