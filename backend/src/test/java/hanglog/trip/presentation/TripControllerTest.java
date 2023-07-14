package hanglog.trip.presentation;

import static hanglog.trip.restdocs.RestDocsConfiguration.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.trip.presentation.dto.request.TripCreateRequest;
import hanglog.trip.presentation.dto.request.TripUpdateRequest;
import hanglog.trip.restdocs.RestDocsConfiguration;
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
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.JsonFieldType;

@WebMvcTest(TripController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
class TripControllerTest extends RestDocsTest {

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

        mockMvc.perform(post("/trips")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tripCreateRequest)));
    }

    private void performPutWhenBadRequestCase(final TripUpdateRequest updateRequest, final String errMessage)
            throws Exception {
        mockMvc.perform(put("/trips/" + 1)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(errMessage));
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

        // when & then
        mockMvc.perform(post("/trips")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripCreateRequest)))
                .andExpect(status().isCreated())
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
        final TripCreateRequest tripCreateRequest = new TripCreateRequest(
                null,
                LocalDate.of(2023, 7, 7),
                List.of(1L, 2L)
        );

        // when & then
        mockMvc.perform(post("/trips")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripCreateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("여행 시작 날짜를 입력해주세요."));
    }

    @DisplayName("여행 종료 날짜를 입력하지 않으면 예외가 발생한다.")
    @Test
    void createTrip_EndDateNull() throws Exception {
        // given
        final TripCreateRequest tripCreateRequest = new TripCreateRequest(
                LocalDate.of(2023, 7, 2),
                null,
                List.of(1L, 2L)
        );

        // when & then
        mockMvc.perform(post("/trips")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripCreateRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("입력받은 도시 리스트의 길이가 0이면 예외가 발생한다.")
    @Test
    void createTrip_CitesNull() throws Exception {
        // given
        final TripCreateRequest tripCreateRequest = new TripCreateRequest(
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                null
        );

        // when & then
        mockMvc.perform(post("/trips")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripCreateRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("입력받은 도시 리스트의 길이가 0이면 예외가 발생한다.")
    @Test
    void createTrip_CitesEmpty() throws Exception {
        // given
        final TripCreateRequest tripCreateRequest = new TripCreateRequest(
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                Collections.EMPTY_LIST
        );
        new TripCreateRequest(null, null, null);

        // when & then
        mockMvc.perform(post("/trips")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripCreateRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("트립의 정보를 변경할 수 있다.")
    @Test
    void updateTrip() throws Exception {
        // given
        makeTrip();

        final TripUpdateRequest updateRequest = new TripUpdateRequest(
                "변경된 타이틀",
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                "추가된 여행 설명",
                List.of(1L, 2L, 3L)
        );

        // when & then
        mockMvc.perform(put("/trips/" + 1)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNoContent());

        verify(tripService).update(anyLong(), any(TripUpdateRequest.class));
    }

    @DisplayName("타이틀을 입력하지 않으면 예외가 발생한다.")
    @Test
    void updateTrip_TitleNull() throws Exception {
        // given
        makeTrip();

        final TripUpdateRequest updateRequest = new TripUpdateRequest(
                null,
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                "추가된 여행 설명",
                List.of(1L, 2L, 3L)
        );

        // when & then
        performPutWhenBadRequestCase(updateRequest, "여행 제목을 입력해 주세요.");
    }

    @DisplayName("타이틀을 길이가 50자를 초과하면 예외가 발생한다.")
    @Test
    void updateTrip_TitleOverMax() throws Exception {
        // given
        makeTrip();

        final String updatedTitle = "1" + "1234567890".repeat(5);
        final TripUpdateRequest updateRequest = new TripUpdateRequest(
                updatedTitle,
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                "추가된 여행 설명",
                List.of(1L, 2L, 3L)
        );

        // when & then
        performPutWhenBadRequestCase(updateRequest, "여행 제목은 50자를 넘을 수 없습니다.");
    }


    @DisplayName("타이틀을 길이가 50자를 초과하면 예외가 발생한다.")
    @Test
    void updateTrip_DescriptionOverMax() throws Exception {
        // given
        makeTrip();

        final String updateDescription = "1" + "1234567890".repeat(20);
        final TripUpdateRequest updateRequest = new TripUpdateRequest(
                "updatedTite",
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                updateDescription,
                List.of(1L, 2L, 3L)
        );

        // when & then
        performPutWhenBadRequestCase(updateRequest, "여행 요약은 200자를 넘을 수 없습니다.");
    }

    @DisplayName("여행 시작 날짜를 입력하지 않으면 예외가 발생한다.")
    @Test
    void updateTrip_StartDateNull() throws Exception {
        // given
        makeTrip();

        final TripUpdateRequest updateRequest = new TripUpdateRequest(
                "변경된 타이틀",
                null,
                LocalDate.of(2023, 7, 7),
                "추가된 여행 설명",
                List.of(1L, 2L, 3L)
        );

        // when & then
        performPutWhenBadRequestCase(updateRequest, "여행 시작 날짜를 입력해 주세요.");
    }

    @DisplayName("여행 종료 날짜를 입력하지 않으면 예외가 발생한다.")
    @Test
    void updateTrip_EndDateNull() throws Exception {
        // given
        makeTrip();

        final TripUpdateRequest updateRequest = new TripUpdateRequest(
                "변경된 타이틀",
                LocalDate.of(2023, 7, 1),
                null,
                "추가된 여행 설명",
                List.of(1L, 2L, 3L)
        );

        // when & then
        performPutWhenBadRequestCase(updateRequest, "여행 종료 날짜를 입력해 주세요.");
    }

    @DisplayName("도시Id 리스트를 입력하지 않으면 예외가 발생한다.")
    @Test
    void updateTrip_CityIdsNull() throws Exception {
        // given
        makeTrip();

        final TripUpdateRequest updateRequest = new TripUpdateRequest(
                "변경된 타이틀",
                LocalDate.of(2023, 7, 1),
                LocalDate.of(2023, 7, 7),
                "추가된 여행 설명",
                null
        );

        // when & then
        performPutWhenBadRequestCase(updateRequest, "여행한 도시는 최소 한 개 이상 입력해 주세요.");
    }

    @DisplayName("도시Id를 빈 리스트로 요청하면 예외가 발생한다.")
    @Test
    void updateTrip_CityIdsEmpty() throws Exception {
        // given
        makeTrip();

        final TripUpdateRequest updateRequest = new TripUpdateRequest(
                "변경된 타이틀",
                LocalDate.of(2023, 7, 1),
                LocalDate.of(2023, 7, 7),
                "추가된 여행 설명",
                Collections.EMPTY_LIST
        );

        // when & then
        performPutWhenBadRequestCase(updateRequest, "여행한 도시는 최소 한 개 이상 입력해 주세요.");
    }

    @DisplayName("트립의 status를 DELETED로 변경할 수 있다.")
    @Test
    void deleteTrip() throws Exception {
        // given
        makeTrip();

        // when & then
        mockMvc.perform(delete("/trips/" + 1)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(tripService).delete(anyLong());
    }
}
