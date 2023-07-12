package hanglog.trip.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.LOCATION;
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
                "apiId",
                "에펠탑",
                "에펠탑주소",
                new BigDecimal(38.123456),
                new BigDecimal(39.123456),
                1L
        );

        final ExpenseRequest expenseRequest = new ExpenseRequest("EURO", 10000, "문화");

        final ItemRequest itemRequest = new ItemRequest(true,
                "에펠탑",
                1,
                4.5,
                "에펠탑을 방문",
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
}
