package hanglog.admin.presentation;

import static hanglog.global.restdocs.RestDocsConfiguration.field;
import static hanglog.trip.fixture.CityFixture.PARIS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.city.dto.request.CityRequest;
import hanglog.city.dto.response.CityDetailResponse;
import hanglog.city.service.CityService;
import hanglog.global.ControllerTest;
import hanglog.login.domain.MemberTokens;
import jakarta.servlet.http.Cookie;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(AdminCityController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class AdminCityControllerTest extends ControllerTest {

    private static final MemberTokens MEMBER_TOKENS = new MemberTokens("refreshToken", "accessToken");
    private static final Cookie COOKIE = new Cookie("refresh-token", MEMBER_TOKENS.getRefreshToken());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CityService cityService;

    @BeforeEach
    void setUp() {
        given(refreshTokenRepository.existsById(any())).willReturn(true);
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
        given(adminMemberRepository.existsByIdAndAdminType(any(), any())).willReturn(false);
    }

    @DisplayName("도시 상세 목록을 조회한다.")
    @Test
    void getCitiesDetail() throws Exception {
        // given
        final CityDetailResponse response = CityDetailResponse.of(PARIS);

        given(cityService.getAllCitiesDetail()).willReturn(List.of(response));

        // when & then
        mockMvc.perform(get("/admin/cities")
                        .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                        .cookie(COOKIE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(response.getId()))
                .andExpect(jsonPath("$[0].name").value(response.getName()))
                .andExpect(jsonPath("$[0].country").value(response.getCountry()))
                .andExpect(jsonPath("$[0].latitude").value(response.getLatitude()))
                .andExpect(jsonPath("$[0].longitude").value(response.getLongitude()))
                .andDo(restDocs.document(
                        responseFields(
                                fieldWithPath("[].id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("도시 ID")
                                        .attributes(field("constraint", "양의 정수")),
                                fieldWithPath("[].name")
                                        .type(JsonFieldType.STRING)
                                        .description("도시 이름")
                                        .attributes(field("constraint", "20자 이내의 문자열")),
                                fieldWithPath("[].country")
                                        .type(JsonFieldType.STRING)
                                        .description("나라 이름")
                                        .attributes(field("constraint", "20자 이내의 문자열")),
                                fieldWithPath("[].latitude")
                                        .type(JsonFieldType.NUMBER)
                                        .description("위도")
                                        .attributes(field("constraint", "BigDecimal(3,13)")),
                                fieldWithPath("[].longitude")
                                        .type(JsonFieldType.NUMBER)
                                        .description("경도")
                                        .attributes(field("constraint", "BigDecimal(3,13)"))
                        )
                ));
    }

    @DisplayName("새로운 도시를 생성한다.")
    @Test
    void createCity() throws Exception {
        // given
        final CityRequest request = new CityRequest(
                "name",
                "country",
                BigDecimal.valueOf(123.12345),
                BigDecimal.valueOf(123.12345)
        );
        given(cityService.save(any(CityRequest.class))).willReturn(1L);

        // when & then
        mockMvc.perform(post("/admin/cities")
                        .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                        .cookie(COOKIE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/admin/cities/1"))
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("name")
                                        .type(JsonFieldType.STRING)
                                        .description("도시 이름")
                                        .attributes(field("constraint", "20자 이내의 문자열")),
                                fieldWithPath("country")
                                        .type(JsonFieldType.STRING)
                                        .description("나라 이름")
                                        .attributes(field("constraint", "20자 이내의 문자열")),
                                fieldWithPath("latitude")
                                        .type(JsonFieldType.NUMBER)
                                        .description("위도")
                                        .attributes(field("constraint", "BigDecimal(3,13)")),
                                fieldWithPath("longitude")
                                        .type(JsonFieldType.NUMBER)
                                        .description("경도")
                                        .attributes(field("constraint", "BigDecimal(3,13)"))
                        )
                ));
    }

    @DisplayName("도시 정보를 수정한다.")
    @Test
    void updateCity() throws Exception {
        // given
        final CityRequest request = new CityRequest(
                "newName",
                "country",
                BigDecimal.valueOf(123.12345),
                BigDecimal.valueOf(123.12345)
        );
        doNothing().when(cityService).update(1L, request);

        // when & then
        mockMvc.perform(put("/admin/cities/{cityId}", 1)
                        .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                        .cookie(COOKIE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("cityId")
                                        .description("도시 ID")
                        ),
                        requestFields(
                                fieldWithPath("name")
                                        .type(JsonFieldType.STRING)
                                        .description("도시 이름")
                                        .attributes(field("constraint", "20자 이내의 문자열")),
                                fieldWithPath("country")
                                        .type(JsonFieldType.STRING)
                                        .description("나라 이름")
                                        .attributes(field("constraint", "20자 이내의 문자열")),
                                fieldWithPath("latitude")
                                        .type(JsonFieldType.NUMBER)
                                        .description("위도")
                                        .attributes(field("constraint", "BigDecimal(3,13)")),
                                fieldWithPath("longitude")
                                        .type(JsonFieldType.NUMBER)
                                        .description("경도")
                                        .attributes(field("constraint", "BigDecimal(3,13)"))
                        )
                ));
    }
}
