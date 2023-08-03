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

    INVALID_RATING(3001, "별점은 N.0점이거나 N.5점 형태이어야 합니다."),
    INVALID_CURRENCY(3002, "제공하지 않는 통화입니다."),

    INVALID_ORDERED_ITEM_IDS(4001, "날짜에 속한 모든 여행 아이템들의 ID가 필요합니다."),

    EXCEED_IMAGE_CAPACITY(5001, "업로드 가능한 이미지 용량을 초과했습니다."),
    NULL_IMAGE(5002, "업로드한 이미지 파일이 NULL입니다."),
    EMPTY_IMAGE_LIST(5003, "최소 한 장 이상의 이미지를 업로드해야합니다."),
    EXCEED_IMAGE_LIST_SIZE(5004, "업로드 가능한 이미지 개수를 초과했습니다."),
    INVALID_IMAGE_URL(5005, "요청한 이미지 URL의 형식이 잘못되었습니다."),
    INVALID_IMAGE_PATH(5101, "이미지를 저장할 경로가 올바르지 않습니다."),
    FAIL_IMAGE_NAME_HASH(5102, "이미지 이름을 해싱하는 데 실패했습니다."),

    INTERNAL_SEVER_ERROR(9999, "서버 에러가 발생하였습니다. 관리자에게 문의해 주세요.");

    private final int code;
    private final String message;
}
