package hanglog.global.exception;

import static hanglog.global.exception.ExceptionCode.IMAGE_SIZE_EXCEED;

import java.util.Objects;
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
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // TODO: Logger 달기

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatusCode status,
                                                                  final WebRequest request) {
        final String errMessage = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(0000, errMessage));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(final BadRequestException e) {
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<ExceptionResponse> handleSizeLimitExceededException(final SizeLimitExceededException e) {
        final String message = IMAGE_SIZE_EXCEED.getMessage()
                + " 입력된 이미지 용량은 " + e.getActualSize() + " byte 입니다. "
                + "(제한 용량: " + e.getPermittedSize() + " byte)";
        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(IMAGE_SIZE_EXCEED.getCode(), message));
    }
}
