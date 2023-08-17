package hanglog.trip.presentation;

import static hanglog.trip.fixture.CityFixture.LONDON;
import static hanglog.trip.fixture.CityFixture.PARIS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.city.dto.response.CityResponse;
import hanglog.global.ControllerTest;
import hanglog.trip.service.CityService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(CityController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class CityControllerTest extends ControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CityService cityService;

    @DisplayName("모든 도시를 조회한다.")
    @Test
    void getCities() throws Exception {
        // given
        when(cityService.getAllCities()).thenReturn(
                List.of(CityResponse.withCountry(PARIS), CityResponse.withCountry(LONDON))
        );

        // when
        final ResultActions resultActions = mockMvc.perform(get("/cities")
                .contentType(APPLICATION_JSON));

        // then
        final MvcResult mvcResult = resultActions.andExpect(status().isOk())
                .andDo(restDocs.document())
                .andReturn();

        final List<CityResponse> cityResponses = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        assertThat(cityResponses).usingRecursiveComparison()
                .isEqualTo(List.of(CityResponse.withCountry(PARIS), CityResponse.withCountry(LONDON)));
    }
}
