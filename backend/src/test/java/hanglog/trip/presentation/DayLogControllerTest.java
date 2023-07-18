package hanglog.trip.presentation;

import static hanglog.trip.restdocs.RestDocsConfiguration.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.trip.presentation.dto.request.DayLogUpdateTitleRequest;
import hanglog.trip.presentation.dto.response.DayLogGetResponse;
import hanglog.trip.restdocs.RestDocsTest;
import hanglog.trip.service.DayLogService;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.JsonFieldType;


@WebMvcTest(DayLogController.class)
@MockBean(JpaMetamodelMappingContext.class)
class DayLogControllerTest extends RestDocsTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DayLogService dayLogService;

    @DisplayName("날짜별 여행을 조회할 수 있다.")
    @Test
    void getDayLog() throws Exception {
        // given
        final DayLogGetResponse response = new DayLogGetResponse(
                1L,
                "런던 여행 첫날",
                1,
                LocalDate.of(2023, 7, 1),
                new ArrayList<>());

        given(dayLogService.getById(1L))
                .willReturn(response);

        // when & then
        mockMvc.perform(get("/trips/{tripId}/daylog/{dayLogId}", 1L, 1L))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("tripId")
                                                .description("여행 ID"),
                                        parameterWithName("dayLogId")
                                                .description("날짜별 기록 ID")
                                ),
                                responseFields(
                                        fieldWithPath("id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("날짜별 기록 ID")
                                                .attributes(field("constraint", "양의 정수")),
                                        fieldWithPath("title")
                                                .type(JsonFieldType.STRING)
                                                .description("소제목")
                                                .attributes(field("constraint", "문자열")),
                                        fieldWithPath("ordinal")
                                                .type(JsonFieldType.NUMBER)
                                                .description("여행에서의 날짜 순서")
                                                .attributes(field("constraint", "양의 정수")),
                                        fieldWithPath("date")
                                                .type(JsonFieldType.STRING)
                                                .description("실제 날짜")
                                                .attributes(field("constraint", "yyyy-MM-dd")),
                                        fieldWithPath("items")
                                                .type(JsonFieldType.ARRAY)
                                                .description("아이템 목록")
                                                .attributes(field("constraint", "배열"))
                                )
                        )
                );
    }

    @DisplayName("날짜별 제목을 수정할 수 있다.")
    @Test
    void updateDayLogTitle() throws Exception {
        // given
        final DayLogUpdateTitleRequest request = new DayLogUpdateTitleRequest("updated");

        doNothing().when(dayLogService).updateTitle(anyLong(), any(DayLogUpdateTitleRequest.class));

        // when & then
        mockMvc.perform(patch("/trips/{tripId}/daylog/{dayLogId}", 1L, 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("tripId")
                                                .description("여행 ID"),
                                        parameterWithName("dayLogId")
                                                .description("날짜별 기록 ID")
                                ),
                                requestFields(
                                        fieldWithPath("title")
                                                .type(JsonFieldType.STRING)
                                                .description("수정된 제목")
                                                .attributes(field("constraint", "문자열"))
                                )
                        )
                );
    }
}
