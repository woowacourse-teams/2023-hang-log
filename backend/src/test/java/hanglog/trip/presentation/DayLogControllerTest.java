package hanglog.trip.presentation;

import static hanglog.trip.restdocs.RestDocsConfiguration.field;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import hanglog.trip.presentation.dto.response.DayLogGetResponse;
import hanglog.trip.restdocs.RestDocsTest;
import hanglog.trip.service.DayLogService;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.JsonFieldType;


@WebMvcTest(DayLogController.class)
@MockBean(JpaMetamodelMappingContext.class)
class DayLogControllerTest extends RestDocsTest {

    @MockBean
    private DayLogService dayLogService;

    @DisplayName("단일 여행을 생성할 수 있다.")
    @Test
    void getDayLog() throws Exception {
        // given
        final DayLogGetResponse response = new DayLogGetResponse(1L, "런던 여행", 1, new ArrayList<>());

        given(dayLogService.getDayLogById(1L))
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
                                        fieldWithPath("items")
                                                .type(JsonFieldType.ARRAY)
                                                .description("아이템 목록")
                                                .attributes(field("constraint", "배열"))
                                )
                        )
                );
    }
}
