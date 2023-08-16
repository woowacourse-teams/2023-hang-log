package hanglog.expense.presentation;

import static hanglog.category.fixture.CategoryFixture.EXPENSE_CATEGORIES;
import static hanglog.expense.fixture.AmountFixture.AMOUNT_20000;
import static hanglog.expense.fixture.CurrencyFixture.DEFAULT_CURRENCY;
import static hanglog.trip.fixture.CityFixture.LONDON;
import static hanglog.trip.fixture.CityFixture.TOKYO;
import static hanglog.trip.fixture.DayLogFixture.EXPENSE_LONDON_DAYLOG;
import static hanglog.trip.fixture.TripFixture.LONDON_TO_JAPAN;
import static hanglog.trip.fixture.TripFixture.LONDON_TRIP;
import static hanglog.trip.restdocs.RestDocsConfiguration.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import hanglog.auth.domain.MemberTokens;
import hanglog.expense.domain.CategoryExpense;
import hanglog.expense.domain.DayLogExpense;
import hanglog.expense.dto.response.TripExpenseResponse;
import hanglog.expense.service.ExpenseService;
import hanglog.global.ControllerTest;
import hanglog.trip.domain.TripCity;
import hanglog.trip.service.TripService;
import jakarta.servlet.http.Cookie;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(ExpenseController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ExpenseControllerTest extends ControllerTest {

    private static final MemberTokens MEMBER_TOKENS = new MemberTokens("refreshToken", "accessToken");
    private static final Cookie COOKIE = new Cookie("refresh-token", MEMBER_TOKENS.getRefreshToken());

    @MockBean
    private ExpenseService expenseService;

    @MockBean
    private TripService tripService;

    private ResultActions performGetRequest(final int tripId) throws Exception {
        return mockMvc.perform(
                get("/trips/{tripId}/expense", tripId)
                        .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                        .cookie(COOKIE)
                        .contentType(APPLICATION_JSON));
    }

    @DisplayName("모든 경비를 가져온다.")
    @Test
    void getExpenses() throws Exception {
        // given
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
        doNothing().when(tripService).validateTripByMember(anyLong(), anyLong());

        final TripExpenseResponse tripExpenseResponse = TripExpenseResponse.of(
                LONDON_TO_JAPAN,
                AMOUNT_20000,
                List.of(new TripCity(LONDON_TRIP, LONDON), new TripCity(LONDON_TRIP, TOKYO)),
                List.of(new CategoryExpense(EXPENSE_CATEGORIES.get(1), AMOUNT_20000, AMOUNT_20000)),
                DEFAULT_CURRENCY,
                List.of(new DayLogExpense(EXPENSE_LONDON_DAYLOG, AMOUNT_20000))
        );

        // when & then
        when(expenseService.getAllExpenses(1L)).thenReturn(tripExpenseResponse);

        // when
        final ResultActions resultActions = performGetRequest(1);

        // then
        resultActions.andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("tripId")
                                                .description("여행 ID")
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
