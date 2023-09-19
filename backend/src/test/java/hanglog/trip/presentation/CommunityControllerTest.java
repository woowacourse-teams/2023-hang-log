package hanglog.trip.presentation;

import static hanglog.global.restdocs.RestDocsConfiguration.field;
import static hanglog.trip.fixture.CityFixture.LONDON;
import static hanglog.trip.fixture.CityFixture.PARIS;
import static hanglog.trip.fixture.TripFixture.LONDON_TRIP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.auth.domain.MemberTokens;
import hanglog.city.domain.City;
import hanglog.global.ControllerTest;
import hanglog.trip.dto.response.CommunitySingleTripResponse;
import hanglog.trip.dto.response.CommunityTripsResponse;
import hanglog.trip.service.CommunityService;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;


@WebMvcTest(CommunityController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class CommunityControllerTest extends ControllerTest {

    private static final List<City> CITIES = List.of(PARIS, LONDON);
    private static final MemberTokens MEMBER_TOKENS = new MemberTokens("refreshToken", "accessToken");
    private static final Cookie COOKIE = new Cookie("refresh-token", MEMBER_TOKENS.getRefreshToken());

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommunityService communityService;

    @BeforeEach
    void setUp() {
        given(refreshTokenRepository.existsByToken(any())).willReturn(true);
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
    }

    private ResultActions performGetRequest() throws Exception {
        return mockMvc.perform(get("/community/trips")
                .queryParam("page", "0")
                .queryParam("size", "1")
                .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                .cookie(COOKIE)
                .contentType(APPLICATION_JSON));
    }

    @DisplayName("로그인 된 상태에서 ")
    @Test
    void getTripsByPage() throws Exception {
        // given
        when(communityService.getTripsByPage(any(), any()))
                .thenReturn(
                        new CommunityTripsResponse(
                                List.of(CommunitySingleTripResponse.of(LONDON_TRIP, CITIES, true, 1L)),
                                1L
                        )
                );

        // when
        final ResultActions resultActions = performGetRequest();

        // then
        final MvcResult mvcResult = resultActions.andExpect(status().isOk())
                .andDo(restDocs.document(
                        responseFields(
                                fieldWithPath("trips")
                                        .type(JsonFieldType.ARRAY)
                                        .description("여행 목록")
                                        .attributes(field("constraint", "10개 이하의 여행")),
                                fieldWithPath("trips[].id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("여행 ID")
                                        .attributes(field("constraint", "양의 정수")),
                                fieldWithPath("trips[].title")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 제목")
                                        .attributes(field("constraint", "50자 이하의 문자열")),
                                fieldWithPath("trips[].startDate")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 시작 날짜")
                                        .attributes(field("constraint", "yyyy-MM-dd")),
                                fieldWithPath("trips[].endDate")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 종료 날짜")
                                        .attributes(field("constraint", "yyyy-MM-dd")),
                                fieldWithPath("trips[].description")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 요약")
                                        .attributes(field("constraint", "200자 이하의 문자열")),
                                fieldWithPath("trips[].likeCount")
                                        .type(JsonFieldType.NUMBER)
                                        .description("좋아요 숫자")
                                        .attributes(field("constraint", "0과 양의 정수")),
                                fieldWithPath("trips[].isLike")
                                        .type(JsonFieldType.BOOLEAN)
                                        .description("좋아요 유무")
                                        .attributes(field("constraint", "boolean (true : 좋아요)")),
                                fieldWithPath("trips[].imageUrl")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 대표 이미지")
                                        .attributes(field("constraint", "이미지 URL")),
                                fieldWithPath("trips[].cities")
                                        .type(JsonFieldType.ARRAY)
                                        .description("여행 도시 배열")
                                        .attributes(field("constraint", "1개 이상의 도시 정보")),
                                fieldWithPath("trips[].cities[].id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("여행 도시 ID")
                                        .attributes(field("constraint", "양의 정수")),
                                fieldWithPath("trips[].cities[].name")
                                        .type(JsonFieldType.STRING)
                                        .description("여행 도시 이름")
                                        .attributes(field("constraint", "도시")),
                                fieldWithPath("lastPageIndex")
                                        .type(JsonFieldType.NUMBER)
                                        .description("마지막 페이지 인덱스")
                                        .attributes(field("constraint", "양의 정수"))
                        )
                ))
                .andReturn();

        final CommunityTripsResponse communityTripsResponses = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        assertThat(communityTripsResponses).usingRecursiveComparison()
                .isEqualTo(
                        new CommunityTripsResponse(
                                List.of(CommunitySingleTripResponse.of(LONDON_TRIP, CITIES, true, 1L)),
                                1L
                        )
                );
    }
}
