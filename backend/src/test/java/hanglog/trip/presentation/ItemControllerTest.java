package hanglog.trip.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.trip.presentation.dto.request.ExpenseRequest;
import hanglog.trip.presentation.dto.request.ItemRequest;
import hanglog.trip.presentation.dto.request.PlaceRequest;
import hanglog.trip.service.ItemService;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ItemController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
        final ExpenseRequest expenseRequest = new ExpenseRequest("EURO", 10000, 1L);
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
        mockMvc.perform(post("/trips/1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, "/trips/1/items/1"));
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

        final ExpenseRequest expenseRequest = new ExpenseRequest("EURO", 10000, 1L);

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
