package hanglog.global.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    INVALID_REQUEST(1000, "올바르지 않은 요청입니다."),

    NOT_FOUND_TRIP_ID(1001, "요청한 ID에 해당하는 여행이 존재하지 않습니다."),
    NOT_FOUND_DAY_LOG_ID(1002, "요청한 ID에 해당하는 데이로그가 존재하지 않습니다."),
    NOT_FOUND_TRIP_ITEM_ID(1003, "요청한 ID에 해당하는 여행 아이템이 존재하지 않습니다."),
    NOT_FOUND_EXPENSE_ID(1004, "요청한 ID에 해당하는 금액이 존재하지 않습니다."),
    NOT_FOUND_CITY_ID(1005, "요청한 ID에 해당하는 도시가 존재하지 않습니다."),
    NOT_FOUND_PLACE_ID(1006, "요청한 ID에 해당하는 장소가 존재하지 않습니다."),
    NOT_FOUND_CATEGORY_ID(1007, "요청한 ID에 해당하는 카테고리가 존재하지 않습니다."),
    NOT_FOUNT_IMAGE_URL(1008, "요청한 URL에 해당하는 이미지가 존재하지 않습니다."),
    NOT_FOUND_CURRENCY_DATA(1009, "요청한 날짜에 해당하는 환율 정보가 존재하지 않습니다."),
    NOT_FOUND_MEMBER_ID(1010, "요청한 ID에 해당하는 멤버가 존재하지 않습니다."),
    INVALID_TRIP_WITH_MEMBER(1011, "요청한 멤버와 ID에 해당하는 여행이 존재하지 않습니다."),
    FAIL_TO_GENERATE_RANDOM_NICKNAME(1012, "랜덤한 닉네임을 생성하는데 실패하였습니다."),
    DUPLICATED_MEMBER_NICKNAME(1013, "중복된 닉네임입니다."),

    ALREADY_DELETED_TRIP_ITEM(2001, "이미 삭제된 여행 아이템입니다."),
    ALREADY_DELETED_DATE(2002, "이미 삭제된 날짜입니다."),

    INVALID_RATING(3001, "별점은 N.0점이거나 N.5점 형태이어야 합니다."),
    INVALID_CURRENCY(3002, "제공하지 않는 통화입니다."),
    INVALID_NULL_PLACE(3003, "아이템의 장소 정보가 필요합니다."),
    INVALID_NOT_NULL_PLACE(3004, "아이템의 장소 정보가 불필요합니다."),
    INVALID_IS_PLACE_UPDATED_WHEN_NON_SPOT(3005, "아이템이 기타일 때, 장소를 업데이트할 수 없습니다."),
    INVALID_CURRENCY_DATE_WHEN_WEEKEND(3006, "주말의 공공 환율 api를 조회할 수 없습니다."),
    INVALID_DATE_ALREADY_EXIST(3007, "요청한 날짜의 환율 정보는 이미 존재합니다."),
    INVALID_EXPENSE_OVER_MAX(3008, "금액이 1억원보다 클 수 없습니다."),
    INVALID_EXPENSE_UNDER_MIN(3009, "금액이 0원보다 작을 수 없습니다."),

    INVALID_ORDERED_ITEM_IDS(4001, "날짜에 속한 모든 여행 아이템들의 ID가 필요합니다."),

    EXCEED_IMAGE_CAPACITY(5001, "업로드 가능한 이미지 용량을 초과했습니다."),
    NULL_IMAGE(5002, "업로드한 이미지 파일이 NULL입니다."),
    EMPTY_IMAGE_LIST(5003, "최소 한 장 이상의 이미지를 업로드해야합니다."),
    EXCEED_IMAGE_LIST_SIZE(5004, "업로드 가능한 이미지 개수를 초과했습니다."),
    INVALID_IMAGE_URL(5005, "요청한 이미지 URL의 형식이 잘못되었습니다."),
    INVALID_IMAGE_PATH(5101, "이미지를 저장할 경로가 올바르지 않습니다."),
    FAIL_IMAGE_NAME_HASH(5102, "이미지 이름을 해싱하는 데 실패했습니다."),
    INVALID_IMAGE(5103, "올바르지 않은 이미지 파일입니다."),

    NOT_ASSOCIATE_DAYLOG_WITH_TRIP(6001, "요청한 DayLog와 Trip은 연관관계가 아닙니다."),

    NOT_FOUND_SHARED_CODE(7001, "요청한 URL에 해당하는 공유된 여행이 존재하지 않습니다."),
    INVALID_SHARE_CODE(7002, "공유가 허용되지 않은 코드입니다."),
    FAIL_SHARE_CODE_HASH(7101, "공유 코드를 해싱하는 데 실패했습니다."),

    INVALID_PUBLISHED_STATUS_REQUEST(8001, "여행의 현재 공개 상태와 다른 상태로만 변경 할 수 있습니다."),
    NOT_FOUND_PUBLISHED_TRIP_WITH_TRIP(8002, "요청한 여행에 해당하는 공개 여행이 존재하지 않습니다."),

    INVALID_AUTHORIZATION_CODE(9001, "유효하지 않은 인증 코드입니다."),
    NOT_SUPPORTED_OAUTH_SERVICE(9002, "해당 OAuth 서비스는 제공하지 않습니다."),
    FAIL_TO_CONVERT_URL_PARAMETER(9003, "Url Parameter 변환 중 오류가 발생했습니다."),
    INVALID_REFRESH_TOKEN(9101, "올바르지 않은 형식의 RefreshToken입니다."),
    INVALID_ACCESS_TOKEN(9102, "올바르지 않은 형식의 AccessToken입니다."),
    EXPIRED_PERIOD_REFRESH_TOKEN(9103, "기한이 만료된 RefreshToken입니다."),
    EXPIRED_PERIOD_ACCESS_TOKEN(9104, "기한이 만료된 AccessToken입니다."),
    FAIL_TO_VALIDATE_TOKEN(9105, "토큰 유효성 검사 중 오류가 발생했습니다."),
    NOT_FOUND_REFRESH_TOKEN(9106, "refresh-token에 해당하는 쿠키 정보가 없습니다."),
    INVALID_AUTHORITY(9201, "해당 요청에 대한 접근 권한이 없습니다."),

    INTERNAL_SEVER_ERROR(9999, "서버 에러가 발생하였습니다. 관리자에게 문의해 주세요.");

    private final int code;
    private final String message;
}
