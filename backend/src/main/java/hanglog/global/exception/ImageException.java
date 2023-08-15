package hanglog.global.exception;

import lombok.Getter;

@Getter
public class ImageException extends BadRequestException {

    public ImageException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
