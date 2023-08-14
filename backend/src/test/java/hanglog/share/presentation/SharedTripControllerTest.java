package hanglog.share.presentation;

import static hanglog.share.fixture.ShareFixture.BEIJING;
import static hanglog.share.fixture.ShareFixture.CALIFORNIA;
import static hanglog.share.fixture.ShareFixture.TOKYO;
import static hanglog.share.fixture.ShareFixture.TRIP;
import static hanglog.trip.restdocs.RestDocsConfiguration.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.share.dto.request.SharedTripStatusRequest;
import hanglog.share.dto.response.SharedTripCodeResponse;
import hanglog.share.service.SharedTripService;
import hanglog.trip.dto.response.TripDetailResponse;
import hanglog.trip.restdocs.RestDocsTest;
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
class SharedTripControllerTest extends RestDocsTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SharedTripService sharedTripService;

    @DisplayName("ShareCode로 단일 여행을 조회한다.")
    @Test
    void getSharedTrip() throws Exception {
        // given
        when(sharedTripService.getTripDetail(anyString()))
                .thenReturn(TripDetailResponse.of(TRIP, List.of(CALIFORNIA, TOKYO, BEIJING)));

        // when
        mockMvc.perform(get("/shared-trips/{sharedCode}", "xxxxxx").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
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
                                fieldWithPath("description")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 요약")
                                        .attributes(field("constraint", "200자 이하의 문자열")),
                                fieldWithPath("imageUrl")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 대표 이미지")
                                        .attributes(field("constraint", "이미지 URL")),
                                fieldWithPath("sharedCode")
                                        .type(JsonFieldType.STRING)
                                        .description("공유 코")
                                        .attributes(field("constraint", "문자열 비공유시 null 입력"))
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

    @DisplayName("공유 상태를 변경한다")
    @Test
    void updateSharedStatus() throws Exception {
        // given
        final SharedTripStatusRequest sharedStatusRequest = new SharedTripStatusRequest(true);
        final SharedTripCodeResponse sharedCodeResponse = new SharedTripCodeResponse("sharedCode");
        when(sharedTripService.updateSharedStatus(anyLong(), any(SharedTripStatusRequest.class)))
                .thenReturn(sharedCodeResponse);

        // when & then
        mockMvc.perform(patch("/trips/{tripId}/share", 1)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sharedStatusRequest)))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                                pathParameters(
                                        parameterWithName("tripId")
                                                .description("여행 ID")
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
        final SharedTripStatusRequest sharedStatusRequest = new SharedTripStatusRequest(null);
        final SharedTripCodeResponse sharedCodeResponse = new SharedTripCodeResponse("xxxxxx");
        when(sharedTripService.updateSharedStatus(anyLong(), any(SharedTripStatusRequest.class)))
                .thenReturn(sharedCodeResponse);

        // when & then
        mockMvc.perform(patch("/trips/{tripId}/share", 1)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sharedStatusRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("공유 상태를 선택해주세요."));
    }
}
