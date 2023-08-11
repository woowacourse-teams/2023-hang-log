package hanglog.global.exception;

import lombok.Getter;

@Getter
public class InvalidCurrencyDateException extends RuntimeException {

    private final int code;
    private final String message;

    public InvalidCurrencyDateException(final ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}
