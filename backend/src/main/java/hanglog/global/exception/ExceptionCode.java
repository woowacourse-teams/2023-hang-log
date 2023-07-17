package hanglog.global.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    INVALID_REQUEST(0000, "올바르지 않은 요청입니다."),
    NOT_FOUND_DAY_LOG_ID(1000, "요청한 ID에 해당하는 데이로그가 존재하지 않습니다."),
    ALREADY_DELETED_DATE(1001, "이미 삭제된 날짜입니다.");

    private final int code;
    private final String message;
}
