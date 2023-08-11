package hanglog.member.presentation;

import static hanglog.trip.restdocs.RestDocsConfiguration.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
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
import hanglog.member.dto.AccessTokenRequest;
import hanglog.member.dto.AccessTokenResponse;
import hanglog.member.dto.LoginRequest;
import hanglog.member.domain.MemberTokens;
import hanglog.member.service.AuthService;
import hanglog.trip.restdocs.RestDocsTest;
import jakarta.servlet.http.Cookie;
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

    private final static String GOOGLE_PROVIDER = "google";
    private final static String REFRESH_TOKEN = "refreshToken";
    private final static String ACCESS_TOKEN = "accessToken";
    private final static String RENEW_ACCESS_TOKEN = "I'mNewAccessToken!";

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @DisplayName("로그인을 할 수 있다.")
    @Test
    void login() throws Exception {
        // given
        final LoginRequest loginRequest = new LoginRequest("code");
        final MemberTokens memberTokens = new MemberTokens(REFRESH_TOKEN, ACCESS_TOKEN);

        when(authService.login(anyString(), anyString()))
                .thenReturn(memberTokens);

        final ResultActions resultActions = mockMvc.perform(post("/login/{provider}", GOOGLE_PROVIDER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest))
        );

        final MvcResult mvcResult = resultActions.andExpect(status().isOk())
                .andDo(restDocs.document(
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
                                        .attributes(field("constraint", "문자열(jwt)"))
                        )
                ))
                .andExpect(cookie().exists("refresh-token"))
                .andExpect(cookie().value("refresh-token", memberTokens.getRefreshToken()))
                .andReturn();

        final AccessTokenResponse expected = new AccessTokenResponse(memberTokens.getAccessToken());

        final AccessTokenResponse actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                AccessTokenResponse.class
        );

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("accessToken 재발급을 통해 로그인을 연장할 수 있다.")
    @Test
    void extendLogin() throws Exception {
        // given
        final MemberTokens memberTokens = new MemberTokens(REFRESH_TOKEN, RENEW_ACCESS_TOKEN);
        final Cookie cookie = new Cookie("refresh-token", memberTokens.getRefreshToken());
        final AccessTokenRequest accessTokenRequest = new AccessTokenRequest(ACCESS_TOKEN);

        when(authService.renewalAccessToken(REFRESH_TOKEN, ACCESS_TOKEN))
                .thenReturn(RENEW_ACCESS_TOKEN);

        // when
        final ResultActions resultActions = mockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accessTokenRequest))
                .cookie(cookie)
        );

        final MvcResult mvcResult = resultActions.andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("accessToken")
                                        .type(JsonFieldType.STRING)
                                        .description("access token")
                                        .attributes(field("constraint", "문자열(jwt)"))
                        ),
                        responseFields(
                                fieldWithPath("accessToken")
                                        .type(JsonFieldType.STRING)
                                        .description("access token")
                                        .attributes(field("constraint", "문자열(jwt)"))
                        )
                ))
                .andReturn();

        final AccessTokenResponse expected = new AccessTokenResponse(memberTokens.getAccessToken());

        final AccessTokenResponse actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                AccessTokenResponse.class
        );

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("멤버의 refreshToken을 삭제하고 로그아웃 할 수 있다.")
    @Test
    void logout() throws Exception {
        // given
        final MemberTokens memberTokens = new MemberTokens(REFRESH_TOKEN, RENEW_ACCESS_TOKEN);
        final Cookie cookie = new Cookie("refresh-token", memberTokens.getRefreshToken());
        final AccessTokenRequest accessTokenRequest = new AccessTokenRequest(ACCESS_TOKEN);

        // when
        final ResultActions resultActions = mockMvc.perform(post("/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accessTokenRequest))
                .cookie(cookie)
        );

        resultActions.andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("accessToken")
                                        .type(JsonFieldType.STRING)
                                        .description("access token")
                                        .attributes(field("constraint", "문자열(jwt)"))
                        )
                ));

        // then
        verify(authService).removeMemberRefreshToken(anyString(), anyString());
    }
}
