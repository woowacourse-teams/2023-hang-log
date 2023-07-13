package hanglog.trip.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.trip.presentation.dto.request.TripRequest;
import hanglog.trip.presentation.dto.request.TripUpdateRequest;
import hanglog.trip.service.TripService;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TripController.class)
@MockBean(JpaMetamodelMappingContext.class)
class TripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TripService tripService;

    private void makeTrip() throws Exception {
        final TripRequest tripRequest = new TripRequest(
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                List.of(1L, 2L)
        );

        when(tripService.save(any(TripRequest.class)))
                .thenReturn(1L);

        mockMvc.perform(post("/trips")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tripRequest)));
    }

    @DisplayName("단일 여행을 생성할 수 있다.")
    @Test
    void createTrip() throws Exception {
        // given
        final TripRequest tripRequest = new TripRequest(
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                List.of(1L, 2L)
        );

        when(tripService.save(any(TripRequest.class)))
                .thenReturn(1L);

        // when & then
        mockMvc.perform(post("/trips")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, "/trips/1"));
    }

    @DisplayName("여행 시작 날짜를 입력하지 않으면 예외가 발생한다.")
    @Test
    void createTrip_StartDateNull() throws Exception {
        // given
        final TripRequest tripRequest = new TripRequest(
                null,
                LocalDate.of(2023, 7, 7),
                List.of(1L, 2L)
        );

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
        final TripRequest tripRequest = new TripRequest(
                LocalDate.of(2023, 7, 2),
                null,
                List.of(1L, 2L)
        );

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
        final TripRequest tripRequest = new TripRequest(
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                null
        );

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
        final TripRequest tripRequest = new TripRequest(
                LocalDate.of(2023, 7, 2),
                LocalDate.of(2023, 7, 7),
                Collections.EMPTY_LIST
        );
        new TripRequest(null, null, null);

        // when & then
        mockMvc.perform(post("/trips")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripRequest)))
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
        mockMvc.perform(put("/trips/" + 1)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("여행 제목을 입력해 주세요."));
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
        mockMvc.perform(put("/trips/" + 1)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("여행 시작 날짜를 입력해 주세요."));
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
        mockMvc.perform(put("/trips/" + 1)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("여행 종료 날짜를 입력해 주세요."));
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
        mockMvc.perform(put("/trips/" + 1)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("여행한 도시는 최소 한 개 이상 입력해 주세요."));
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
        mockMvc.perform(put("/trips/" + 1)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("여행한 도시는 최소 한 개 이상 입력해 주세요."));
    }
}
