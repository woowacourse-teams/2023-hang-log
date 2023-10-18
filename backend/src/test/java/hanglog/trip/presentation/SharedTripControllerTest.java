package hanglog.trip.presentation;

import static hanglog.category.fixture.CategoryFixture.EXPENSE_CATEGORIES;
import static hanglog.expense.fixture.AmountFixture.AMOUNT_20000;
import static hanglog.expense.fixture.CurrencyFixture.DEFAULT_CURRENCY;
import static hanglog.global.restdocs.RestDocsConfiguration.field;
import static hanglog.trip.fixture.CityFixture.LONDON;
import static hanglog.trip.fixture.DayLogFixture.EXPENSE_LONDON_DAYLOG;
import static hanglog.trip.fixture.TripFixture.LONDON_TO_JAPAN;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.expense.domain.CategoryExpense;
import hanglog.global.ControllerTest;
import hanglog.login.domain.MemberTokens;
import hanglog.trip.domain.DayLogExpense;
import hanglog.trip.dto.response.LedgerResponse;
import hanglog.trip.dto.response.TripDetailResponse;
import hanglog.trip.fixture.CityFixture;
import hanglog.trip.fixture.ShareFixture;
import hanglog.trip.service.LedgerService;
import hanglog.trip.service.SharedTripService;
import hanglog.trip.service.TripService;
import jakarta.servlet.http.Cookie;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.JsonFieldType;

@WebMvcTest(SharedTripController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class SharedTripControllerTest extends ControllerTest {

    private static final MemberTokens MEMBER_TOKENS = new MemberTokens("refreshToken", "accessToken");
    private static final Cookie COOKIE = new Cookie("refresh-token", MEMBER_TOKENS.getRefreshToken());

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SharedTripService sharedTripService;

    @MockBean
    private TripService tripService;

    @MockBean
    private LedgerService ledgerService;

    @DisplayName("ShareCode로 단일 여행을 조회한다.")
    @Test
    void getSharedTrip() throws Exception {
        // given
        when(sharedTripService.getTripId(anyString()))
                .thenReturn(1L);
        when(sharedTripService.getSharedTripDetail(anyLong()))
                .thenReturn(TripDetailResponse.sharedTrip(ShareFixture.TRIP_SHARE, List.of(ShareFixture.CALIFORNIA, ShareFixture.TOKYO, ShareFixture.BEIJING)));

        // when
        mockMvc.perform(get("/shared-trips/{sharedCode}", "xxxxxx").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("sharedCode")
                                        .description("공유 코드")
                        ),
                        responseFields(
                                fieldWithPath("tripType")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 응답 종류")
                                        .attributes(field("constraint", "문자열 'PRIVATE'")),
                                fieldWithPath("id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("여행 ID")
                                        .attributes(field("constraint", "양의 정수")),
                                fieldWithPath("writer")
                                        .type(JsonFieldType.OBJECT)
                                        .description("작성자"),
                                fieldWithPath("writer.nickname")
                                        .type(JsonFieldType.STRING)
                                        .description("작성자 닉네임")
                                        .attributes(field("constraint", "문자열")),
                                fieldWithPath("writer.imageUrl")
                                        .type(JsonFieldType.STRING)
                                        .description("작성자 이미지")
                                        .attributes(field("constraint", "문자열")),
                                fieldWithPath("isWriter")
                                        .type(JsonFieldType.BOOLEAN)
                                        .description("본인 작성 여부")
                                        .attributes(field("constraint", "true: 본인 작성, false: 타인 작성"))
                                        .optional(),
                                fieldWithPath("title")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 제목")
                                        .attributes(field("constraint", "50자 이하의 문자열")),
                                fieldWithPath("startDate")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 시작 날짜")
                                        .attributes(field("constraint", "yyyy-MM-dd")),
                                fieldWithPath("endDate")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 종료 날짜")
                                        .attributes(field("constraint", "yyyy-MM-dd")),
                                fieldWithPath("description")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 요약")
                                        .attributes(field("constraint", "200자 이하의 문자열")),
                                fieldWithPath("imageName")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 대표 이미지")
                                        .attributes(field("constraint", "이미지 이름")),
                                fieldWithPath("sharedCode")
                                        .type(JsonFieldType.STRING)
                                        .description("공유 코드")
                                        .attributes(field("constraint", "문자열 비공유시 null"))
                                        .optional(),
                                fieldWithPath("isLike")
                                        .type(JsonFieldType.BOOLEAN)
                                        .description("좋아요 여부")
                                        .attributes(field("constraint", "true : 본인 좋아요, false : 본인 싫어요"))
                                        .optional(),
                                fieldWithPath("likeCount")
                                        .type(JsonFieldType.NUMBER)
                                        .description("좋아요 갯수")
                                        .attributes(field("constraint", "0 또는 양의 정수"))
                                        .optional(),
                                fieldWithPath("publishedDate")
                                        .type(JsonFieldType.STRING)
                                        .description("공개 날짜")
                                        .attributes(field("constraint", "yyyy-MM-dd-hh-mm-ss"))
                                        .optional(),
                                fieldWithPath("isPublished")
                                        .type(JsonFieldType.BOOLEAN)
                                        .description("공개 여부")
                                        .attributes(field("constraint", "boolean 공개시 true"))
                                        .optional(),
                                fieldWithPath("cities")
                                        .type(JsonFieldType.ARRAY)
                                        .description("여행 도시 배열")
                                        .attributes(field("constraint", "1개 이상의 도시 정보")),
                                fieldWithPath("cities[].id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("여행 도시 ID")
                                        .attributes(field("constraint", "양의 정수")),
                                fieldWithPath("cities[].name")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 도시 이름")
                                        .attributes(field("constraint", "나라, 도시")),
                                fieldWithPath("cities[].latitude")
                                        .type(JsonFieldType.NUMBER)
                                        .description("위도")
                                        .attributes(field("constraint", "실수")),
                                fieldWithPath("cities[].longitude")
                                        .type(JsonFieldType.NUMBER)
                                        .description("경도")
                                        .attributes(field("constraint", "실수")),
                                fieldWithPath("dayLogs")
                                        .type(JsonFieldType.ARRAY)
                                        .description("날짜별 여행 기록 배열")
                                        .attributes(field("constraint", "2개 이상의 데이 로그")),
                                fieldWithPath("dayLogs[0].id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("날짜별 기록 ID")
                                        .attributes(field("constraint", "양의 정수")),
                                fieldWithPath("dayLogs[0].title")
                                        .type(JsonFieldType.STRING)
                                        .description("소제목")
                                        .attributes(field("constraint", "문자열")),
                                fieldWithPath("dayLogs[0].ordinal")
                                        .type(JsonFieldType.NUMBER)
                                        .description("여행에서의 날짜 순서")
                                        .attributes(field("constraint", "양의 정수")),
                                fieldWithPath("dayLogs[0].date")
                                        .type(JsonFieldType.STRING)
                                        .description("실제 날짜")
                                        .attributes(field("constraint", "yyyy-MM-dd")),
                                fieldWithPath("dayLogs[0].items")
                                        .type(JsonFieldType.ARRAY)
                                        .description("아이템 목록")
                                        .attributes(field("constraint", "배열"))
                        )
                ))
                .andReturn();
    }

    @DisplayName("ShareCode로 여행에 대한 가계부를 조회한다.")
    @Test
    void getSharedExpenses() throws Exception {
        // given
        when(sharedTripService.getTripId(anyString()))
                .thenReturn(1L);

        final LedgerResponse ledgerResponse = LedgerResponse.of(
                LONDON_TO_JAPAN,
                AMOUNT_20000,
                List.of(LONDON, CityFixture.TOKYO),
                List.of(new CategoryExpense(EXPENSE_CATEGORIES.get(1), AMOUNT_20000, AMOUNT_20000)),
                DEFAULT_CURRENCY,
                List.of(new DayLogExpense(EXPENSE_LONDON_DAYLOG, AMOUNT_20000))
        );

        // when
        when(ledgerService.getAllExpenses(1L)).thenReturn(ledgerResponse);

        // then
        mockMvc.perform(get("/shared-trips/{sharedCode}/expense", "xxxxxx").contentType(APPLICATION_JSON))
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("sharedCode")
                                                .description("공유 코드")
                                ),
                                responseFields(
                                        fieldWithPath("id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("여행 ID")
                                                .attributes(field("constraint", "양의 정수")),
                                        fieldWithPath("title")
                                                .type(JsonFieldType.STRING)
                                                .description("여행 제목")
                                                .attributes(field("constraint", "50자 이하의 문자열")),
                                        fieldWithPath("startDate")
                                                .type(JsonFieldType.STRING)
                                                .description("여행 시작 날짜")
                                                .attributes(field("constraint", "yyyy-MM-dd")),
                                        fieldWithPath("endDate")
                                                .type(JsonFieldType.STRING)
                                                .description("여행 종료 날짜")
                                                .attributes(field("constraint", "yyyy-MM-dd")),
                                        fieldWithPath("cities")
                                                .type(JsonFieldType.ARRAY)
                                                .description("도시 목록")
                                                .attributes(field("constraint", "1개 이상의 city")),
                                        fieldWithPath("cities[].id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("도시 ID")
                                                .attributes(field("constraint", "양의 정수")),
                                        fieldWithPath("cities[].name")
                                                .type(JsonFieldType.STRING)
                                                .description("도시 명")
                                                .attributes(field("constraint", "20자 이하의 문자열")),
                                        fieldWithPath("totalAmount")
                                                .type(JsonFieldType.NUMBER)
                                                .description("총 경비")
                                                .attributes(field("constraint", "양의 정수")),
                                        fieldWithPath("categories")
                                                .type(JsonFieldType.ARRAY)
                                                .description("카테고리별 경비 목록")
                                                .attributes(field("constraint", "카테고리 배열")),
                                        fieldWithPath("categories[].category")
                                                .type(JsonFieldType.OBJECT)
                                                .description("카테고리")
                                                .attributes(field("constraint", "카테고리")),
                                        fieldWithPath("categories[].category.id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("카테고리 ID")
                                                .attributes(field("constraint", "양의 정수")),
                                        fieldWithPath("categories[].category.name")
                                                .type(JsonFieldType.STRING)
                                                .description("카테고리 명")
                                                .attributes(field("constraint", "2자의 문자열")),
                                        fieldWithPath("categories[].amount")
                                                .type(JsonFieldType.NUMBER)
                                                .description("카테고리 경비")
                                                .attributes(field("constraint", "양의 정수")),
                                        fieldWithPath("categories[].percentage")
                                                .type(JsonFieldType.NUMBER)
                                                .description("카테고리 백분율")
                                                .attributes(field("constraint", "100이하의 백분율")),
                                        fieldWithPath("exchangeRate")
                                                .type(JsonFieldType.OBJECT)
                                                .description("적용된 환율")
                                                .attributes(field("constraint", "적용된 환율")),
                                        fieldWithPath("exchangeRate.date")
                                                .type(JsonFieldType.STRING)
                                                .description("환율 날짜")
                                                .attributes(field("constraint", "yyyy-MM-dd")),
                                        fieldWithPath("exchangeRate.currencyRates")
                                                .type(JsonFieldType.ARRAY)
                                                .description("통화별 환율")
                                                .attributes(field("constraint", "currency")),
                                        fieldWithPath("exchangeRate.currencyRates[].currency")
                                                .type(JsonFieldType.STRING)
                                                .description("통화")
                                                .attributes(field("constraint", "3자의 문자열")),
                                        fieldWithPath("exchangeRate.currencyRates[].rate")
                                                .type(JsonFieldType.NUMBER)
                                                .description("환율")
                                                .attributes(field("constraint", "양의 유리수")),
                                        fieldWithPath("dayLogs")
                                                .type(JsonFieldType.ARRAY)
                                                .description("날짜별 여행 기록 배열")
                                                .attributes(field("constraint", "2개 이상의 데이 로그")),
                                        fieldWithPath("dayLogs[].id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("날짜별 기록 ID")
                                                .attributes(field("constraint", "양의 정수")),
                                        fieldWithPath("dayLogs[].ordinal")
                                                .type(JsonFieldType.NUMBER)
                                                .description("여행에서의 날짜 순서")
                                                .attributes(field("constraint", "양의 정수")),
                                        fieldWithPath("dayLogs[].date")
                                                .type(JsonFieldType.STRING)
                                                .description("실제 날짜")
                                                .attributes(field("constraint", "yyyy-MM-dd")),
                                        fieldWithPath("dayLogs[].totalAmount")
                                                .type(JsonFieldType.NUMBER)
                                                .description("날짜별 총 경비")
                                                .attributes(field("constraint", "양의 정수")),
                                        fieldWithPath("dayLogs[].items")
                                                .type(JsonFieldType.ARRAY)
                                                .description("아이템 목록")
                                                .attributes(field("constraint", "배열")),
                                        fieldWithPath("dayLogs[].items[].id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("아이템 ID")
                                                .attributes(field("constraint", "양의 정수")),
                                        fieldWithPath("dayLogs[].items[].title")
                                                .type(JsonFieldType.STRING)
                                                .description("아이템 제목")
                                                .attributes(field("constraint", "50자 이하의 문자열")),
                                        fieldWithPath("dayLogs[].items[].ordinal")
                                                .type(JsonFieldType.NUMBER)
                                                .description("아이템의 배치 순서")
                                                .attributes(field("constraint", "양의 정수")),
                                        fieldWithPath("dayLogs[].items[].expense")
                                                .type(JsonFieldType.OBJECT)
                                                .description("아이템 경비")
                                                .attributes(field("constraint", "아이템 경비")),
                                        fieldWithPath("dayLogs[].items[].expense.id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("경비 ID")
                                                .attributes(field("constraint", "양의 정수")),
                                        fieldWithPath("dayLogs[].items[].expense.currency")
                                                .type(JsonFieldType.STRING)
                                                .description("경비 통화")
                                                .attributes(field("constraint", "3자의 문자열")),
                                        fieldWithPath("dayLogs[].items[].expense.amount")
                                                .type(JsonFieldType.NUMBER)
                                                .description("경비")
                                                .attributes(field("constraint", "양의 유리수")),
                                        fieldWithPath("dayLogs[].items[].expense.category")
                                                .type(JsonFieldType.OBJECT)
                                                .description("경비 카테고리")
                                                .attributes(field("constraint", "id와 이름")),
                                        fieldWithPath("dayLogs[].items[].expense.category.id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("카테고리 ID")
                                                .attributes(field("constraint", "양의 정수")),
                                        fieldWithPath("dayLogs[].items[].expense.category.name")
                                                .type(JsonFieldType.STRING)
                                                .description("카테고리 명")
                                                .attributes(field("constraint", "2자 문자열"))
                                )
                        )
                )
                .andExpect(status().isOk());
    }
}
