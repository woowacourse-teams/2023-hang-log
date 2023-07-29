package hanglog.global.exception;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger handlerLogger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request
    ) {
        final String errMessage = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        handlerLogger.warn(errMessage, e);
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(0000, errMessage));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(final BadRequestException e) {
        handlerLogger.warn(e.getMessage(), e);
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(final Exception e) {
        handlerLogger.error(e.getMessage(), e);
        return ResponseEntity.internalServerError()
                .body(new ExceptionResponse(9999, e.getMessage()));
    }
}
