package hanglog.admin.presentation;

import static hanglog.currency.fixture.CurrencyFixture.CURRENCY_1;
import static hanglog.currency.fixture.CurrencyFixture.CURRENCY_2;
import static hanglog.global.restdocs.RestDocsConfiguration.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.currency.dto.response.CurrencyListResponse;
import hanglog.currency.dto.response.CurrencyResponse;
import hanglog.currency.service.CurrencyService;
import hanglog.global.ControllerTest;
import hanglog.login.domain.MemberTokens;
import jakarta.servlet.http.Cookie;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(AdminCurrencyController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class AdminCurrencyControllerTest extends ControllerTest {


    private static final MemberTokens MEMBER_TOKENS = new MemberTokens("refreshToken", "accessToken");
    private static final Cookie COOKIE = new Cookie("refresh-token", MEMBER_TOKENS.getRefreshToken());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        given(refreshTokenRepository.existsById(any())).willReturn(true);
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
        given(adminMemberRepository.existsAdminMemberByIdAndAdminType(any(), any())).willReturn(false);
    }

    @DisplayName("도시 상세 목록을 조회한다.")
    @Test
    void getCitiesDetail() throws Exception {
        // given
        final CurrencyResponse response1 = CurrencyResponse.of(CURRENCY_1);
        final CurrencyResponse response2 = CurrencyResponse.of(CURRENCY_2);
        final CurrencyListResponse pageResponse = new CurrencyListResponse(List.of(response1, response2), 1L);

        given(currencyService.getCurrenciesByPage(any())).willReturn(pageResponse);

        // when & then
        mockMvc.perform(get("/admin/currencies")
                        .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                        .cookie(COOKIE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currencies.[0].id").value(response1.getId()))
                .andExpect(jsonPath("$.currencies.[0].date").value(response1.getDate().toString()))
                .andExpect(jsonPath("$.currencies.[0].usd").value(response1.getUsd()))
                .andExpect(jsonPath("$.currencies.[0].eur").value(response1.getEur()))
                .andExpect(jsonPath("$.currencies.[0].gbp").value(response1.getGbp()))
                .andExpect(jsonPath("$.currencies.[0].jpy").value(response1.getJpy()))
                .andExpect(jsonPath("$.currencies.[0].cny").value(response1.getCny()))
                .andExpect(jsonPath("$.currencies.[0].chf").value(response1.getChf()))
                .andExpect(jsonPath("$.currencies.[0].sgd").value(response1.getSgd()))
                .andExpect(jsonPath("$.currencies.[0].thb").value(response1.getThb()))
                .andExpect(jsonPath("$.currencies.[0].hkd").value(response1.getHkd()))
                .andExpect(jsonPath("$.currencies.[0].krw").value(response1.getKrw()))
                .andExpect(jsonPath("$.lastPageIndex").value(pageResponse.getLastPageIndex()))
                .andDo(restDocs.document(
                        responseFields(
                                fieldWithPath("currencies.[].id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("환율 ID")
                                        .attributes(field("constraint", "양의 정수")),
                                fieldWithPath("currencies.[].date")
                                        .type(JsonFieldType.STRING)
                                        .description("날짜")
                                        .attributes(field("constraint", "YYYY-MM-DD")),
                                fieldWithPath("currencies.[].usd")
                                        .type(JsonFieldType.NUMBER)
                                        .description("USD")
                                        .attributes(field("constraint", "double")),
                                fieldWithPath("currencies.[].eur")
                                        .type(JsonFieldType.NUMBER)
                                        .description("EUR")
                                        .attributes(field("constraint", "double")),
                                fieldWithPath("currencies.[].gbp")
                                        .type(JsonFieldType.NUMBER)
                                        .description("GBP")
                                        .attributes(field("constraint", "double")),
                                fieldWithPath("currencies.[].jpy")
                                        .type(JsonFieldType.NUMBER)
                                        .description("JPY")
                                        .attributes(field("constraint", "double")),
                                fieldWithPath("currencies.[].cny")
                                        .type(JsonFieldType.NUMBER)
                                        .description("CNY")
                                        .attributes(field("constraint", "double")),
                                fieldWithPath("currencies.[].chf")
                                        .type(JsonFieldType.NUMBER)
                                        .description("CHF")
                                        .attributes(field("constraint", "double")),
                                fieldWithPath("currencies.[].sgd")
                                        .type(JsonFieldType.NUMBER)
                                        .description("SGD")
                                        .attributes(field("constraint", "double")),
                                fieldWithPath("currencies.[].thb")
                                        .type(JsonFieldType.NUMBER)
                                        .description("THB")
                                        .attributes(field("constraint", "double")),
                                fieldWithPath("currencies.[].hkd")
                                        .type(JsonFieldType.NUMBER)
                                        .description("HKD")
                                        .attributes(field("constraint", "double")),
                                fieldWithPath("currencies.[].krw")
                                        .type(JsonFieldType.NUMBER)
                                        .description("KRW")
                                        .attributes(field("constraint", "double")),
                                fieldWithPath("lastPageIndex")
                                        .type(JsonFieldType.NUMBER)
                                        .description("마지막 페이지 인덱스")
                                        .attributes(field("constraint", "양의 정수"))
                        )
                ));
    }
}
