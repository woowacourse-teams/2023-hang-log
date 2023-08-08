package hanglog.currency.presentation;

import static hanglog.expense.fixture.CurrencyFixture.DEFAULT_CURRENCY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import hanglog.currency.service.CurrencyService;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CurrencyController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @DisplayName("당일 환율 정보를 저장한다.")
    @Test
    void saveTodayCurrency() throws Exception {
        // given
        when(currencyService.saveDailyCurrency(any(LocalDate.class)))
                .thenReturn(DEFAULT_CURRENCY);

        // when & then
        mockMvc.perform(post("/currency/today"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(header().string(LOCATION, "/currency/" + DEFAULT_CURRENCY.getDate()));
    }
}
