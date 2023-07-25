package hanglog.global.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    INVALID_REQUEST(0000, "올바르지 않은 요청입니다."),

    NOT_FOUND_TRIP_ID(1001, "요청한 ID에 해당하는 여행이 존재하지 않습니다."),
    NOT_FOUND_DAY_LOG_ID(1002, "요청한 ID에 해당하는 데이로그가 존재하지 않습니다."),
    NOT_FOUND_TRIP_ITEM_ID(1003, "요청한 ID에 해당하는 여행 아이템이 존재하지 않습니다."),
    NOT_FOUND_EXPENSE_ID(1004, "요청한 ID에 해당하는 금액이 존재하지 않습니다."),
    NOT_FOUND_CITY_ID(1005, "요청한 ID에 해당하는 도시가 존재하지 않습니다."),
    NOT_FOUND_PLACE_ID(1006, "요청한 ID에 해당하는 장소가 존재하지 않습니다."),
    NOT_FOUND_CATEGORY_ID(1007, "요청한 ID에 해당하는 카테고리가 존재하지 않습니다."),
    NOT_FOUNT_IMAGE_URL(1008, "요청한 URL에 해당하는 이미지가 존재하지 않습니다."),

    ALREADY_DELETED_TRIP_ITEM(2001, "이미 삭제된 여행 아이템입니다."),
    ALREADY_DELETED_DATE(2002, "이미 삭제된 날짜입니다."),

    INVALID_RATING(3001, "별점은 N.0점이거나 N.5점 형태이어야 합니다.");

    private final int code;
    private final String message;
}
