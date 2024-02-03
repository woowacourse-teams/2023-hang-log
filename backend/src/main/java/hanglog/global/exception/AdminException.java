package hanglog.global.exception;

import lombok.Getter;

@Getter
public class AdminException extends RuntimeException {

    private final int code;
    private final String message;

    public AdminException(final ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}
