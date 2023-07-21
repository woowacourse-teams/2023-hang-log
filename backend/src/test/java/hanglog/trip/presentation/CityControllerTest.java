package hanglog.trip.presentation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.trip.domain.City;
import hanglog.trip.dto.response.CityResponse;
import hanglog.trip.restdocs.RestDocsTest;
import hanglog.trip.service.CityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CityController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class CityControllerTest extends RestDocsTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CityService cityService;

    @DisplayName("모든 도시를 조회한다.")
    @Test
    void getCities() throws Exception {
        // given
        final City seoul = new City(1L, "서울", "대한민국", new BigDecimal("126.9779692"), new BigDecimal("37.566535"));
        final City busan = new City(2L, "부산", "대한민국", new BigDecimal("129.0756416"), new BigDecimal("35.1795543"));

        when(cityService.getAllCities()).thenReturn(List.of(CityResponse.of(seoul), CityResponse.of(busan)));

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
                .isEqualTo(List.of(CityResponse.of(seoul), CityResponse.of(busan)));
    }
}
