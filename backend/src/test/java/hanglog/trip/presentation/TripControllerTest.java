package hanglog.trip.presentation;

import static hanglog.trip.fixture.CityFixture.LONDON;
import static hanglog.trip.fixture.CityFixture.PARIS;
import static hanglog.trip.fixture.TripFixture.LONDON_TRIP;
import static hanglog.trip.restdocs.RestDocsConfiguration.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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
import hanglog.trip.domain.City;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.dto.request.TripUpdateRequest;
import hanglog.trip.dto.response.TripDetailResponse;
import hanglog.trip.dto.response.TripResponse;
import hanglog.trip.restdocs.RestDocsTest;
import hanglog.trip.service.TripService;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
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
class TripControllerTest extends RestDocsTest {

    private static final List<City> CITIES = List.of(PARIS, LONDON);

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TripService tripService;

    private void makeTrip() throws Exception {
        final TripCreateRequest tripCreateRequest = new TripCreateRequest(
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                List.of(1L, 2L)
        );

        when(tripService.save(any(TripCreateRequest.class)))
                .thenReturn(1L);

        performPostRequest(tripCreateRequest);
    }

    private ResultActions performGetRequest() throws Exception {
        return mockMvc.perform(get("/trips")
                .contentType(APPLICATION_JSON));
    }

    private ResultActions performGetRequest(final int tripId) throws Exception {
        return mockMvc.perform(get("/trips/{tripId}", tripId)
                .contentType(APPLICATION_JSON));
    }

    private ResultActions performPostRequest(final TripCreateRequest tripCreateRequest) throws Exception {
        return mockMvc.perform(post("/trips")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tripCreateRequest)));
    }

    private ResultActions performPutRequest(final TripUpdateRequest updateRequest) throws Exception {
        return mockMvc.perform(put("/trips/{tripId}", 1)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)));
    }

    private ResultActions performDeleteRequest() throws Exception {
        return mockMvc.perform(delete("/trips/{tripId}", 1)
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

        when(tripService.save(any(TripCreateRequest.class)))
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
        when(tripService.getTripDetail(1L))
                .thenReturn(TripDetailResponse.of(LONDON_TRIP, CITIES));

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
                                fieldWithPath("description")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 요약")
                                        .attributes(field("constraint", "200자 이하의 문자열")),
                                fieldWithPath("imageUrl")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 대표 이미지")
                                        .attributes(field("constraint", "이미지 URL")),
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
        when(tripService.getAllTrips())
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
                                fieldWithPath("[].imageUrl")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 대표 이미지")
                                        .attributes(field("constraint", "이미지 URL")),
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

        final TripUpdateRequest updateRequest = new TripUpdateRequest(
                "변경된 타이틀",
                "https://hanglog.com/img/default-image.png",
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
                                        fieldWithPath("imageUrl")
                                                .type(JsonFieldType.STRING)
                                                .description("여행 이미지")
                                                .attributes(field("constraint", "이미지 URL")),
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

        final TripUpdateRequest badRequest = new TripUpdateRequest(
                null,
                "https://hanglog.com/img/default-image.png",
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

        final String updatedTitle = "1" + "1234567890".repeat(5);
        final TripUpdateRequest badRequest = new TripUpdateRequest(
                updatedTitle,
                "https://hanglog.com/img/default-image.png",
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

        final String updateDescription = "1" + "1234567890".repeat(20);
        final TripUpdateRequest badRequest = new TripUpdateRequest(
                "updatedTitle",
                "https://hanglog.com/img/default-image.png",
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

        final TripUpdateRequest badRequest = new TripUpdateRequest(
                "변경된 타이틀",
                "https://hanglog.com/img/default-image.png",
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

        final TripUpdateRequest badRequest = new TripUpdateRequest(
                "변경된 타이틀",
                "https://hanglog.com/img/default-image.png",
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

        final TripUpdateRequest badRequest = new TripUpdateRequest(
                "변경된 타이틀",
                "https://hanglog.com/img/default-image.png",
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

        final TripUpdateRequest badRequest = new TripUpdateRequest(
                "변경된 타이틀",
                "https://hanglog.com/img/default-image.png",
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
}
