package hanglog.global.exception;

import lombok.Getter;

@Getter
public class ImageException extends RuntimeException {

    private final int code;
    private final String message;

    public ImageException(final ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}
