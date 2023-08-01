package hanglog.global.exception;

import static hanglog.global.exception.ExceptionCode.EXCEED_IMAGE_CAPACITY;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request
    ) {
        final String errMessage = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        log.warn(errMessage, e);
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(0000, errMessage));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(final BadRequestException e) {
        log.warn(e.getMessage(), e);
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<ExceptionResponse> handleSizeLimitExceededException(final SizeLimitExceededException e) {
        final String message = EXCEED_IMAGE_CAPACITY.getMessage()
                + " 입력된 이미지 용량은 " + e.getActualSize() + " byte 입니다. "
                + "(제한 용량: " + e.getPermittedSize() + " byte)";
        log.warn(message, e);
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(EXCEED_IMAGE_CAPACITY.getCode(), message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(final Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError()
                .body(new ExceptionResponse(9999, e.getMessage()));
    }
}
