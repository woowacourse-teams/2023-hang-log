package hanglog.admin.presentation;

import static hanglog.category.fixture.CategoryFixture.FOOD;
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
import hanglog.category.dto.response.CategoryDetailResponse;
import hanglog.category.service.CategoryService;
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


@WebMvcTest(AdminCategoryController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class AdminCategoryControllerTest extends ControllerTest {

    private static final MemberTokens MEMBER_TOKENS = new MemberTokens("refreshToken", "accessToken");
    private static final Cookie COOKIE = new Cookie("refresh-token", MEMBER_TOKENS.getRefreshToken());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        given(refreshTokenRepository.existsById(any())).willReturn(true);
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
        given(adminMemberRepository.existsAdminMemberByIdAndAdminType(any(), any())).willReturn(false);
    }

    @DisplayName("카테고리 상세 목록을 조회한다.")
    @Test
    void getCategoriesDetail() throws Exception {
        // given
        final CategoryDetailResponse response = CategoryDetailResponse.of(FOOD);

        given(categoryService.getAllCategoriesDetail()).willReturn(List.of(response));

        // when & then
        mockMvc.perform(get("/admin/categories")
                        .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                        .cookie(COOKIE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(response.getId()))
                .andExpect(jsonPath("$[0].engName").value(response.getEngName()))
                .andExpect(jsonPath("$[0].korName").value(response.getKorName()))
                .andDo(restDocs.document(
                        responseFields(
                                fieldWithPath("[].id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("카테고리 ID")
                                        .attributes(field("constraint", "양의 정수")),
                                fieldWithPath("[].engName")
                                        .type(JsonFieldType.STRING)
                                        .description("영어 이름")
                                        .attributes(field("constraint", "50자 이내의 영어 문자열")),
                                fieldWithPath("[].korName")
                                        .type(JsonFieldType.STRING)
                                        .description("한글 이름")
                                        .attributes(field("constraint", "50자 이내의 한글 문자열"))
                        )
                ));
    }
}
