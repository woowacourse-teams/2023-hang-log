package hanglog.category.presentation;

import static hanglog.category.fixture.CategoryFixture.EXPENSE_CATEGORIES;
import static hanglog.trip.restdocs.RestDocsConfiguration.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.category.dto.CategoryResponse;
import hanglog.category.service.CategoryService;
import hanglog.trip.restdocs.RestDocsTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(CategoryController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class CategoryControllerTest extends RestDocsTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @DisplayName("경비 구분에 필요한 상위 카테고리 정보를 반환한다.")
    @Test
    void getExpenseCategories() throws Exception {
        // given
        final List<CategoryResponse> expectResponses = EXPENSE_CATEGORIES.stream()
                .map(CategoryResponse::of)
                .toList();

        when(categoryService.getExpenseCategories())
                .thenReturn(expectResponses);

        // when & then
        final MvcResult mvcResult = mockMvc.perform(RestDocumentationRequestBuilders.get("/categories")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        responseFields(
                                fieldWithPath("[].id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("카테고리 ID")
                                        .attributes(field("constraint", "양의 정수")),
                                fieldWithPath("[].engName")
                                        .type(JsonFieldType.STRING)
                                        .description("카테고리 영문명")
                                        .attributes(field("constraint", "50자 이하의 문자열")),
                                fieldWithPath("[].korName")
                                        .type(JsonFieldType.STRING)
                                        .description("카테고리 한글명")
                                        .attributes(field("constraint", "50자 이하의 문자열"))
                        )
                ))
                .andReturn();

        final List<CategoryResponse> actualResponses = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        assertThat(actualResponses).usingRecursiveComparison().isEqualTo(expectResponses);
    }
}
