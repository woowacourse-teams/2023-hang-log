package hanglog.trip.presentation;

import static hanglog.trip.restdocs.RestDocsConfiguration.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.trip.presentation.dto.request.TripRequest;
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

    @DisplayName("단일 여행을 생성할 수 있다.")
    @Test
    void createTrip() throws Exception {
        // given
        final TripRequest tripRequest = new TripRequest(LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                List.of(1L, 2L));

        given(tripService.save(any(TripRequest.class)))
                .willReturn(1L);

        // when & then
        mockMvc.perform(post("/trips")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripRequest)))
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
        final TripRequest tripRequest = new TripRequest(null,
                LocalDate.of(2023, 7, 7),
                List.of(1L, 2L));

        // when & then
        mockMvc.perform(post("/trips")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("여행 시작 날짜를 입력해주세요."));
    }

    @DisplayName("여행 종료 날짜를 입력하지 않으면 예외가 발생한다.")
    @Test
    void createTrip_EndDateNull() throws Exception {
        // given
        final TripRequest tripRequest = new TripRequest(LocalDate.of(2023, 7, 2),
                null,
                List.of(1L, 2L));

        // when & then
        mockMvc.perform(post("/trips")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("입력받은 도시 리스트의 길이가 0이면 예외가 발생한다.")
    @Test
    void createTrip_CitesNull() throws Exception {
        // given
        final TripRequest tripRequest = new TripRequest(LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                null);

        // when & then
        mockMvc.perform(post("/trips")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("입력받은 도시 리스트의 길이가 0이면 예외가 발생한다.")
    @Test
    void createTrip_CitesEmpty() throws Exception {
        // given
        final TripRequest tripRequest = new TripRequest(LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                Collections.EMPTY_LIST);

        // when & then
        mockMvc.perform(post("/trips")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripRequest)))
                .andExpect(status().isBadRequest());
    }
}
