package hanglog.trip.presentation;

import static hanglog.trip.restdocs.RestDocsConfiguration.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.trip.dto.request.ExpenseRequest;
import hanglog.trip.dto.request.ItemRequest;
import hanglog.trip.dto.request.PlaceRequest;
import hanglog.trip.restdocs.RestDocsTest;
import hanglog.trip.service.ItemService;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

@WebMvcTest(ItemController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ItemControllerTest extends RestDocsTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;

    @DisplayName("여행 아이템을 생성할 수 있다.")
    @Test
    void createItem() throws Exception {
        // given
        final PlaceRequest placeRequest = new PlaceRequest(
                "에펠탑",
                new BigDecimal("38.123456"),
                new BigDecimal("39.123456"),
                "categoryApiId"
        );
        final ExpenseRequest expenseRequest = new ExpenseRequest("EUR", 10000, 1L);
        final ItemRequest itemRequest = new ItemRequest(
                true,
                "에펠탑",
                4.5,
                "에펠탑을 방문",
                1L,
                placeRequest,
                expenseRequest
        );

        given(itemService.save(any(), any()))
                .willReturn(1L);

        // when & then
        mockMvc.perform(post("/trips/{tripId}/items", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, "/trips/1/items/1"))
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("tripId")
                                                .description("여행 ID")
                                ),
                                requestFields(
                                        fieldWithPath("itemType")
                                                .type(JsonFieldType.BOOLEAN)
                                                .description("여행 아이템의 타입")
                                                .attributes(field("constraint", "True: 스팟, False: 논스팟")),
                                        fieldWithPath("title")
                                                .type(JsonFieldType.STRING)
                                                .description("여행 아이템 제목")
                                                .attributes(field("constraint", "50자 이내의 문자열")),
                                        fieldWithPath("rating")
                                                .type(JsonFieldType.NUMBER)
                                                .description("별점")
                                                .attributes(field("constraint", "최소 0.0 ~ 최대 5.0")),
                                        fieldWithPath("memo")
                                                .type(JsonFieldType.STRING)
                                                .description("여행 아이템 메모")
                                                .attributes(field("constraint", "255자 이내의 문자열")),
                                        fieldWithPath("dayLogId")
                                                .type(JsonFieldType.NUMBER)
                                                .description("날짜 ID")
                                                .attributes(field("constraint", "양의 정수")),
                                        fieldWithPath("place.name")
                                                .type(JsonFieldType.STRING)
                                                .description("장소 이름")
                                                .attributes(field("constraint", "50자 이내의 문자열")),
                                        fieldWithPath("place.latitude")
                                                .type(JsonFieldType.NUMBER)
                                                .description("장소 위도")
                                                .attributes(field("constraint", "BigDecimal(3,13)")),
                                        fieldWithPath("place.longitude")
                                                .type(JsonFieldType.NUMBER)
                                                .description("장소 경도")
                                                .attributes(field("constraint", "BigDecimal(3,13)")),
                                        fieldWithPath("place.apiCategory")
                                                .type(JsonFieldType.STRING)
                                                .description("장소 카테고리 배열")
                                                .attributes(field("constraint", "문자열 배열")),
                                        fieldWithPath("expense.currency")
                                                .type(JsonFieldType.STRING)
                                                .description("경비 환율")
                                                .attributes(field("constraint", "문자열")),
                                        fieldWithPath("expense.amount")
                                                .type(JsonFieldType.NUMBER)
                                                .description("경비 금액")
                                                .attributes(field("constraint", "숫자")),
                                        fieldWithPath("expense.categoryId")
                                                .type(JsonFieldType.NUMBER)
                                                .description("경비 카테고리 ID")
                                                .attributes(field("constraint", "양의 정수"))
                                ),
                                responseHeaders(
                                        headerWithName(LOCATION).description("생성된 아이템 URL")
                                )
                        )
                );
    }

    @DisplayName("여행 아이템을 수정할 수 있다.")
    @Test
    void updateItem() throws Exception {
        //given
        final PlaceRequest placeRequest = new PlaceRequest(
                "에펠탑",
                new BigDecimal("38.123456"),
                new BigDecimal("39.123456"),
                "categoryApiId"
        );

        final ExpenseRequest expenseRequest = new ExpenseRequest("EUR", 10000, 1L);

        final ItemRequest itemRequest = new ItemRequest(
                true,
                "에펠탑",
                4.5,
                "에펠탑을 방문",
                1L,
                placeRequest,
                expenseRequest
        );

        doNothing().when(itemService).update(any(), any(), any());

        // when & then
        mockMvc.perform(put("/trips/1/items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemRequest)))
                .andExpect(status().isNoContent());
    }

    @DisplayName("여행 아이템을 삭제할 수 있다.")
    @Test
    void deleteItem() throws Exception {
        // given
        doNothing().when(itemService).delete(any());

        // when & then
        mockMvc.perform(delete("/trips/1/items/1"))
                .andExpect(status().isNoContent());
    }
}
