package hanglog.global.exception;

import lombok.Getter;

@Getter
public class InvalidCurrencyDateException extends BadRequestException {

    public InvalidCurrencyDateException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
