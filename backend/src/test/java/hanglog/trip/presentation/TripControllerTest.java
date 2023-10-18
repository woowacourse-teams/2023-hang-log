package hanglog.trip.presentation;

import static hanglog.category.fixture.CategoryFixture.EXPENSE_CATEGORIES;
import static hanglog.expense.fixture.AmountFixture.AMOUNT_20000;
import static hanglog.expense.fixture.CurrencyFixture.DEFAULT_CURRENCY;
import static hanglog.global.restdocs.RestDocsConfiguration.field;
import static hanglog.trip.fixture.CityFixture.LONDON;
import static hanglog.trip.fixture.CityFixture.PARIS;
import static hanglog.trip.fixture.CityFixture.TOKYO;
import static hanglog.trip.fixture.DayLogFixture.EXPENSE_LONDON_DAYLOG;
import static hanglog.trip.fixture.TripFixture.LONDON_TO_JAPAN;
import static hanglog.trip.fixture.TripFixture.LONDON_TRIP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.city.domain.City;
import hanglog.expense.domain.CategoryExpense;
import hanglog.global.ControllerTest;
import hanglog.login.domain.MemberTokens;
import hanglog.trip.domain.DayLogExpense;
import hanglog.trip.dto.request.PublishedStatusRequest;
import hanglog.trip.dto.request.SharedStatusRequest;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.dto.request.TripUpdateRequest;
import hanglog.trip.dto.response.SharedCodeResponse;
import hanglog.trip.dto.response.TripDetailResponse;
import hanglog.trip.dto.response.LedgerResponse;
import hanglog.trip.dto.response.TripResponse;
import hanglog.trip.service.LedgerService;
import hanglog.trip.service.TripService;
import jakarta.servlet.http.Cookie;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(TripController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class TripControllerTest extends ControllerTest {

    private static final List<City> CITIES = List.of(PARIS, LONDON);
    private static final MemberTokens MEMBER_TOKENS = new MemberTokens("refreshToken", "accessToken");
    private static final Cookie COOKIE = new Cookie("refresh-token", MEMBER_TOKENS.getRefreshToken());

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TripService tripService;

    @MockBean
    private LedgerService ledgerService;

    @BeforeEach
    void setUp() {
        given(refreshTokenRepository.existsByToken(any())).willReturn(true);
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
    }

    private void makeTrip() throws Exception {
        final TripCreateRequest tripCreateRequest = new TripCreateRequest(
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                List.of(1L, 2L)
        );

        when(tripService.save(anyLong(), any(TripCreateRequest.class)))
                .thenReturn(1L);

        performPostRequest(tripCreateRequest);
    }

    private ResultActions performGetRequest(final int tripId) throws Exception {
        return mockMvc.perform(get("/trips/{tripId}", tripId)
                .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                .cookie(COOKIE)
                .contentType(APPLICATION_JSON));
    }

    private ResultActions performGetRequest() throws Exception {
        return mockMvc.perform(get("/trips")
                .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                .cookie(COOKIE)
                .contentType(APPLICATION_JSON));
    }

    private ResultActions performPostRequest(final TripCreateRequest tripCreateRequest) throws Exception {
        return mockMvc.perform(post("/trips")
                .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                .cookie(COOKIE)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tripCreateRequest)));
    }

    private ResultActions performPutRequest(final TripUpdateRequest updateRequest) throws Exception {
        return mockMvc.perform(put("/trips/{tripId}", 1)
                .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                .cookie(COOKIE)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)));
    }

    private ResultActions performDeleteRequest() throws Exception {
        return mockMvc.perform(delete("/trips/{tripId}", 1)
                .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                .cookie(COOKIE)
                .contentType(APPLICATION_JSON));
    }

    private ResultActions performGetLedgerRequest(final int tripId) throws Exception {
        return mockMvc.perform(
                get("/trips/{tripId}/expense", tripId)
                        .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                        .cookie(COOKIE)
                        .contentType(APPLICATION_JSON));
    }

    @DisplayName("단일 여행을 생성할 수 있다.")
    @Test
    void createTrip() throws Exception {
        // given
        final TripCreateRequest tripCreateRequest = new TripCreateRequest(
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                List.of(1L, 2L)
        );

        when(tripService.save(anyLong(), any(TripCreateRequest.class)))
                .thenReturn(1L);

        // when
        final ResultActions resultActions = performPostRequest(tripCreateRequest);

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, "/trips/1"))
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("startDate")
                                                .type(JsonFieldType.STRING)
                                                .description("여행 시작 날짜")
                                                .attributes(field("constraint", "yyyy-MM-dd")),
                                        fieldWithPath("endDate")
                                                .type(JsonFieldType.STRING)
                                                .description("여행 종료 날짜")
                                                .attributes(field("constraint", "yyyy-MM-dd")),
                                        fieldWithPath("cityIds")
                                                .type(JsonFieldType.ARRAY)
                                                .description("도시 ID 목록")
                                                .attributes(field("constraint", "1개 이상의 양의 정수"))
                                ),
                                responseHeaders(
                                        headerWithName(LOCATION).description("생성된 여행 URL")
                                )
                        )
                );
    }

    @DisplayName("여행 시작 날짜를 입력하지 않으면 예외가 발생한다.")
    @Test
    void createTrip_StartDateNull() throws Exception {
        // given
        final TripCreateRequest badRequest = new TripCreateRequest(
                null,
                LocalDate.of(2023, 7, 7),
                List.of(1L, 2L)
        );

        // when
        final ResultActions resultActions = performPostRequest(badRequest);

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("여행 시작 날짜를 입력해주세요."));
    }

    @DisplayName("여행 종료 날짜를 입력하지 않으면 예외가 발생한다.")
    @Test
    void createTrip_EndDateNull() throws Exception {
        // given
        final TripCreateRequest badRequest = new TripCreateRequest(
                LocalDate.of(2023, 7, 2),
                null,
                List.of(1L, 2L)
        );

        // when
        final ResultActions resultActions = performPostRequest(badRequest);

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("여행 종료 날짜를 입력해주세요."));
    }

    @DisplayName("입력받은 도시 리스트의 길이가 0이면 예외가 발생한다.")
    @Test
    void createTrip_CitesNull() throws Exception {
        // given
        final TripCreateRequest badRequest = new TripCreateRequest(
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                null
        );

        // when
        final ResultActions resultActions = performPostRequest(badRequest);

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("여행한 도시는 최소 한 개 이상 입력해 주세요."));
    }

    @DisplayName("입력받은 도시 리스트의 길이가 0이면 예외가 발생한다.")
    @Test
    void createTrip_CitesEmpty() throws Exception {
        // given
        final TripCreateRequest badRequest = new TripCreateRequest(
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                Collections.EMPTY_LIST
        );

        // when
        final ResultActions resultActions = performPostRequest(badRequest);

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("여행한 도시는 최소 한 개 이상 입력해 주세요."));
    }

    @DisplayName("TripId로 단일 여행을 조회한다.")
    @Test
    void getTrip() throws Exception {
        // given
        makeTrip();
        doNothing().when(tripService).validateTripByMember(anyLong(), anyLong());
        when(tripService.getTripDetail(1L))
                .thenReturn(TripDetailResponse.personalTrip(LONDON_TRIP, CITIES));

        // when
        final ResultActions resultActions = performGetRequest(1);

        // then
        final MvcResult mvcResult = resultActions.andExpect(status().isOk())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("tripId")
                                        .description("여행 ID")
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
                                        .description("작성자")
                                        .optional(),
                                fieldWithPath("writer.nickname")
                                        .type(JsonFieldType.STRING)
                                        .description("작성자 닉네임")
                                        .attributes(field("constraint", "문자열"))
                                        .optional(),
                                fieldWithPath("writer.imageUrl")
                                        .type(JsonFieldType.STRING)
                                        .description("작성자 이미지")
                                        .attributes(field("constraint", "문자열"))
                                        .optional(),
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
                                        .attributes(field("constraint", "boolean 공개시 true")),
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

        final TripResponse tripResponse = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                TripResponse.class
        );
        assertThat(tripResponse).usingRecursiveComparison()
                .isEqualTo(TripResponse.of(LONDON_TRIP, CITIES));
    }


    @DisplayName("모든 여행을 조회한다.")
    @Test
    void getTrips() throws Exception {
        // given
        makeTrip();
        doNothing().when(tripService).validateTripByMember(anyLong(), anyLong());
        when(tripService.getAllTrips(anyLong()))
                .thenReturn(List.of(TripResponse.of(LONDON_TRIP, CITIES)));

        // when
        final ResultActions resultActions = performGetRequest();

        // then
        final MvcResult mvcResult = resultActions.andExpect(status().isOk())
                .andDo(restDocs.document(
                        responseFields(
                                fieldWithPath("[].id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("여행 ID")
                                        .attributes(field("constraint", "양의 정수")),
                                fieldWithPath("[].title")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 제목")
                                        .attributes(field("constraint", "50자 이하의 문자열")),
                                fieldWithPath("[].startDate")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 시작 날짜")
                                        .attributes(field("constraint", "yyyy-MM-dd")),
                                fieldWithPath("[].endDate")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 종료 날짜")
                                        .attributes(field("constraint", "yyyy-MM-dd")),
                                fieldWithPath("[].description")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 요약")
                                        .attributes(field("constraint", "200자 이하의 문자열")),
                                fieldWithPath("[].imageName")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 대표 이미지")
                                        .attributes(field("constraint", "이미지 이름")),
                                fieldWithPath("[].cities")
                                        .type(JsonFieldType.ARRAY)
                                        .description("여행 도시 배열")
                                        .attributes(field("constraint", "1개 이상의 도시 정보")),
                                fieldWithPath("[].cities[].id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("여행 도시 ID")
                                        .attributes(field("constraint", "양의 정수")),
                                fieldWithPath("[].cities[].name")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 도시 이름")
                                        .attributes(field("constraint", "도시"))
                        )
                ))
                .andReturn();

        final List<TripResponse> tripResponses = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        assertThat(tripResponses).usingRecursiveComparison()
                .isEqualTo(List.of(TripResponse.of(LONDON_TRIP, CITIES)));
    }

    @DisplayName("트립의 정보를 변경할 수 있다.")
    @Test
    void updateTrip() throws Exception {
        // given
        makeTrip();
        doNothing().when(tripService).validateTripByMember(anyLong(), anyLong());

        final TripUpdateRequest updateRequest = new TripUpdateRequest(
                "변경된 타이틀",
                "default-image.png",
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                "추가된 여행 설명",
                List.of(1L, 2L, 3L)
        );

        // when
        final ResultActions resultActions = performPutRequest(updateRequest);

        // then
        verify(tripService).update(anyLong(), any(TripUpdateRequest.class));

        resultActions.andExpect(status().isNoContent())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("tripId")
                                                .description("여행 ID")
                                ),
                                requestFields(
                                        fieldWithPath("title")
                                                .type(JsonFieldType.STRING)
                                                .description("여행 제목")
                                                .attributes(field("constraint", "50자 이하의 문자열")),
                                        fieldWithPath("imageName")
                                                .type(JsonFieldType.STRING)
                                                .description("여행 이미지")
                                                .attributes(field("constraint", "이미지 이름")),
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
                                        fieldWithPath("cityIds")
                                                .type(JsonFieldType.ARRAY)
                                                .description("도시 ID 목록")
                                                .attributes(field("constraint", "1개 이상의 양의 정수"))
                                )
                        )
                );
    }

    @DisplayName("제목을 입력하지 않으면 예외가 발생한다.")
    @Test
    void updateTrip_TitleNull() throws Exception {
        // given
        makeTrip();
        doNothing().when(tripService).validateTripByMember(anyLong(), anyLong());

        final TripUpdateRequest badRequest = new TripUpdateRequest(
                null,
                "default-image.png",
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                "추가된 여행 설명",
                List.of(1L, 2L, 3L)
        );

        // when
        final ResultActions resultActions = performPutRequest(badRequest);

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("여행 제목을 입력해 주세요."));
    }


    @DisplayName("제목의 길이가 50자를 초과하면 예외가 발생한다.")
    @Test
    void updateTrip_TitleOverMax() throws Exception {
        // given
        makeTrip();
        doNothing().when(tripService).validateTripByMember(anyLong(), anyLong());

        final String updatedTitle = "1" + "1234567890".repeat(5);
        final TripUpdateRequest badRequest = new TripUpdateRequest(
                updatedTitle,
                "default-image.png",
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                "추가된 여행 설명",
                List.of(1L, 2L, 3L)
        );

        // when
        final ResultActions resultActions = performPutRequest(badRequest);

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("여행 제목은 50자를 넘을 수 없습니다."));
    }


    @DisplayName("요약의 길이가 200자를 초과하면 예외가 발생한다.")
    @Test
    void updateTrip_DescriptionOverMax() throws Exception {
        // given
        makeTrip();
        doNothing().when(tripService).validateTripByMember(anyLong(), anyLong());

        final String updateDescription = "1" + "1234567890".repeat(20);
        final TripUpdateRequest badRequest = new TripUpdateRequest(
                "updatedTitle",
                "default-image.png",
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                updateDescription,
                List.of(1L, 2L, 3L)
        );

        // when
        final ResultActions resultActions = performPutRequest(badRequest);

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("여행 요약은 200자를 넘을 수 없습니다."));
    }

    @DisplayName("여행 시작 날짜를 입력하지 않으면 예외가 발생한다.")
    @Test
    void updateTrip_StartDateNull() throws Exception {
        // given
        makeTrip();
        doNothing().when(tripService).validateTripByMember(anyLong(), anyLong());

        final TripUpdateRequest badRequest = new TripUpdateRequest(
                "변경된 타이틀",
                "default-image.png",
                null,
                LocalDate.of(2023, 7, 7),
                "추가된 여행 설명",
                List.of(1L, 2L, 3L)
        );

        // when
        final ResultActions resultActions = performPutRequest(badRequest);

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("여행 시작 날짜를 입력해 주세요."));
    }

    @DisplayName("여행 종료 날짜를 입력하지 않으면 예외가 발생한다.")
    @Test
    void updateTrip_EndDateNull() throws Exception {
        // given
        makeTrip();
        doNothing().when(tripService).validateTripByMember(anyLong(), anyLong());

        final TripUpdateRequest badRequest = new TripUpdateRequest(
                "변경된 타이틀",
                "default-image.png",
                LocalDate.of(2023, 7, 1),
                null,
                "추가된 여행 설명",
                List.of(1L, 2L, 3L)
        );

        // when
        final ResultActions resultActions = performPutRequest(badRequest);

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("여행 종료 날짜를 입력해 주세요."));
    }

    @DisplayName("도시Id 리스트를 입력하지 않으면 예외가 발생한다.")
    @Test
    void updateTrip_CityIdsNull() throws Exception {
        // given
        makeTrip();
        doNothing().when(tripService).validateTripByMember(anyLong(), anyLong());

        final TripUpdateRequest badRequest = new TripUpdateRequest(
                "변경된 타이틀",
                "default-image.png",
                LocalDate.of(2023, 7, 1),
                LocalDate.of(2023, 7, 7),
                "추가된 여행 설명",
                null
        );

        // when
        final ResultActions resultActions = performPutRequest(badRequest);

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("여행한 도시는 최소 한 개 이상 입력해 주세요."));
    }

    @DisplayName("도시Id를 빈 리스트로 요청하면 예외가 발생한다.")
    @Test
    void updateTrip_CityIdsEmpty() throws Exception {
        // given
        makeTrip();
        doNothing().when(tripService).validateTripByMember(anyLong(), anyLong());

        final TripUpdateRequest badRequest = new TripUpdateRequest(
                "변경된 타이틀",
                "default-image.png",
                LocalDate.of(2023, 7, 1),
                LocalDate.of(2023, 7, 7),
                "추가된 여행 설명",
                Collections.EMPTY_LIST
        );

        // when
        final ResultActions resultActions = performPutRequest(badRequest);

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("여행한 도시는 최소 한 개 이상 입력해 주세요."));
    }

    @DisplayName("트립의 status를 DELETED로 변경할 수 있다.")
    @Test
    void deleteTrip() throws Exception {
        // given
        makeTrip();
        doNothing().when(tripService).validateTripByMember(anyLong(), anyLong());

        // when
        final ResultActions resultActions = performDeleteRequest();

        // then
        verify(tripService).delete(anyLong());

        resultActions.andExpect(status().isNoContent())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("tripId")
                                        .description("여행 ID")
                        )
                ));
    }

    @DisplayName("공유 상태를 변경한다")
    @Test
    void updateSharedStatus() throws Exception {
        // given
        final SharedStatusRequest sharedStatusRequest = new SharedStatusRequest(true);
        final SharedCodeResponse sharedCodeResponse = new SharedCodeResponse("sharedCode");
        when(tripService.updateSharedTripStatus(anyLong(), any(SharedStatusRequest.class)))
                .thenReturn(sharedCodeResponse);
        given(refreshTokenRepository.existsByToken(any())).willReturn(true);
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
        doNothing().when(tripService).validateTripByMember(anyLong(), anyLong());

        // when & then
        mockMvc.perform(patch("/trips/{tripId}/share", 1)
                        .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                        .cookie(COOKIE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sharedStatusRequest)))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                                pathParameters(
                                        parameterWithName("tripId")
                                                .description("여행 ID")
                                ),
                                requestFields(
                                        fieldWithPath("sharedStatus")
                                                .type(JsonFieldType.BOOLEAN)
                                                .description("공유 유무")
                                                .attributes(field("constraint", "공유시: true, 비공유시: false"))
                                ),
                                responseFields(
                                        fieldWithPath("sharedCode")
                                                .type(JsonFieldType.STRING)
                                                .description("공유 코드")
                                                .attributes(field("constraint", "공유시: 문자열 비공유시: null"))
                                                .optional()
                                )
                        )
                )
                .andReturn();
    }

    @DisplayName("공유 상태가 없는 공유 수정 요청은 예외처리한다.")
    @Test
    void getSharedTrip_NullSharedStatus() throws Exception {
        // given
        final SharedStatusRequest sharedStatusRequest = new SharedStatusRequest(null);
        final SharedCodeResponse sharedCodeResponse = new SharedCodeResponse("xxxxxx");
        when(tripService.updateSharedTripStatus(anyLong(), any(SharedStatusRequest.class)))
                .thenReturn(sharedCodeResponse);
        given(refreshTokenRepository.existsByToken(any())).willReturn(true);
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
        doNothing().when(tripService).validateTripByMember(anyLong(), anyLong());

        // when & then
        mockMvc.perform(patch("/trips/{tripId}/share", 1)
                        .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                        .cookie(COOKIE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sharedStatusRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("공유 상태를 선택해주세요."));
    }

    @DisplayName("커뮤니티 공개 상태를 변경한다")
    @Test
    void updatePublishedStatus() throws Exception {
        // given
        final PublishedStatusRequest publishedStatusRequest = new PublishedStatusRequest(true);
        given(refreshTokenRepository.existsByToken(any())).willReturn(true);
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
        doNothing().when(tripService).validateTripByMember(anyLong(), anyLong());

        // when & then
        mockMvc.perform(patch("/trips/{tripId}/publish", 1)
                        .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                        .cookie(COOKIE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(publishedStatusRequest)))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document(
                                pathParameters(
                                        parameterWithName("tripId")
                                                .description("여행 ID")
                                ),
                                requestFields(
                                        fieldWithPath("publishedStatus")
                                                .type(JsonFieldType.BOOLEAN)
                                                .description("공개 상태")
                                )
                        )
                )
                .andReturn();
    }

    @DisplayName("특정 여행의 가계부를 가져온다.")
    @Test
    void getLedger() throws Exception {
        // given
        given(refreshTokenRepository.existsByToken(any())).willReturn(true);
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
        doNothing().when(tripService).validateTripByMember(anyLong(), anyLong());

        final LedgerResponse ledgerResponse = LedgerResponse.of(
                LONDON_TO_JAPAN,
                AMOUNT_20000,
                List.of(LONDON, TOKYO),
                List.of(new CategoryExpense(EXPENSE_CATEGORIES.get(1), AMOUNT_20000, AMOUNT_20000)),
                DEFAULT_CURRENCY,
                List.of(new DayLogExpense(EXPENSE_LONDON_DAYLOG, AMOUNT_20000))
        );

        when(ledgerService.getAllExpenses(1L)).thenReturn(ledgerResponse);

        // when
        final ResultActions resultActions = performGetLedgerRequest(1);

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
