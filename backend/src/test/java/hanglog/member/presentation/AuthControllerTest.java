package hanglog.member.presentation;

import static hanglog.trip.restdocs.RestDocsConfiguration.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.member.dto.AccessTokenResponse;
import hanglog.member.dto.LoginRequest;
import hanglog.member.dto.MemberTokens;
import hanglog.member.service.AuthService;
import hanglog.trip.restdocs.RestDocsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(AuthController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class AuthControllerTest extends RestDocsTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @DisplayName("로그인을 할 수 있다.")
    @Test
    void login() throws Exception {
        // given
        final String provider = "google";
        final LoginRequest loginRequest = new LoginRequest("code");
        final MemberTokens memberTokens = new MemberTokens("refreshToken", "accessToken");

        when(authService.login(anyString(), anyString()))
                .thenReturn(memberTokens);

        // when
        final ResultActions resultActions = mockMvc.perform(post("/login/{provider}", provider)
                .param("loginRequest", loginRequest.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest))
        );

        final MvcResult mvcResult = resultActions.andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("provider")
                                                .description("로그인 유형")
                                ),
                                requestFields(
                                        fieldWithPath("code")
                                                .type(JsonFieldType.STRING)
                                                .description("인가 코드")
                                                .attributes(field("constraint", "문자열"))
                                ),
                                responseFields(
                                        fieldWithPath("accessToken")
                                                .type(JsonFieldType.STRING)
                                                .description("access token")
                                                .attributes(field("constraint", "문자열"))
                                )
                        )
                )
                .andExpect(cookie().exists("refresh-token"))
                .andExpect(cookie().value("refresh-token", memberTokens.getRefreshToken()))
                .andReturn();

        final AccessTokenResponse expectResponse = new AccessTokenResponse(memberTokens.getAccessToken());

        final AccessTokenResponse actualResponse = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                AccessTokenResponse.class
        );

        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectResponse);
    }
}
