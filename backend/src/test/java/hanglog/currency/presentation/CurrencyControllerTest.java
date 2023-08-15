package hanglog.currency.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import hanglog.currency.service.CurrencyService;
import hanglog.global.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CurrencyController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CurrencyControllerTest extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @DisplayName("당일 환율 정보를 저장한다.")
    @Test
    void saveTodayCurrency() throws Exception {
        // when & then
        mockMvc.perform(post("/currency/today"))
                .andExpect(status().isOk());
    }
}
