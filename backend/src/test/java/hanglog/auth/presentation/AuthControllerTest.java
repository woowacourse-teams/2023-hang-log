package hanglog.auth.presentation;

import static hanglog.global.restdocs.RestDocsConfiguration.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.auth.domain.MemberTokens;
import hanglog.auth.dto.AccessTokenResponse;
import hanglog.auth.dto.LoginRequest;
import hanglog.auth.service.AuthService;
import hanglog.global.ControllerTest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(AuthController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class AuthControllerTest extends ControllerTest {

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

        // when
        final MvcResult mvcResult = resultActions.andExpect(status().isCreated())
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

        when(authService.renewalAccessToken(REFRESH_TOKEN, ACCESS_TOKEN))
                .thenReturn(RENEW_ACCESS_TOKEN);

        // when
        final ResultActions resultActions = mockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, ACCESS_TOKEN)
                .cookie(cookie)
        );

        final MvcResult mvcResult = resultActions.andExpect(status().isCreated())
                .andDo(restDocs.document(
                        requestCookies(
                                cookieWithName("refresh-token")
                                        .description("갱신 토큰")
                        ),
                        requestHeaders(
                                headerWithName("Authorization")
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
        given(refreshTokenRepository.existsByToken(any())).willReturn(true);
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
        doNothing().when(authService).removeRefreshToken(anyString());

        final MemberTokens memberTokens = new MemberTokens(REFRESH_TOKEN, RENEW_ACCESS_TOKEN);
        final Cookie cookie = new Cookie("refresh-token", memberTokens.getRefreshToken());

        // when
        final ResultActions resultActions = mockMvc.perform(delete("/logout")
                .header(HttpHeaders.AUTHORIZATION, ACCESS_TOKEN)
                .cookie(cookie)
        );

        resultActions.andExpect(status().isNoContent())
                .andDo(restDocs.document(
                        requestCookies(
                                cookieWithName("refresh-token")
                                        .description("갱신 토큰")
                        ),
                        requestHeaders(
                                headerWithName("Authorization")
                                        .description("access token")
                                        .attributes(field("constraint", "문자열(jwt)"))
                        )
                ));

        // then
        verify(authService).removeRefreshToken(anyString());
    }


    @DisplayName("회원을 탈퇴 할 수 있다.")
    @Test
    void deleteAccount() throws Exception {
        // given
        given(refreshTokenRepository.existsByToken(any())).willReturn(true);
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
        doNothing().when(authService).deleteAccount(anyLong());

        final MemberTokens memberTokens = new MemberTokens(REFRESH_TOKEN, RENEW_ACCESS_TOKEN);
        final Cookie cookie = new Cookie("refresh-token", memberTokens.getRefreshToken());

        // when
        final ResultActions resultActions = mockMvc.perform(delete("/account")
                .header(HttpHeaders.AUTHORIZATION, ACCESS_TOKEN)
                .cookie(cookie)
        );

        resultActions.andExpect(status().isNoContent())
                .andDo(restDocs.document(
                        requestCookies(
                                cookieWithName("refresh-token")
                                        .description("갱신 토큰")
                        ),
                        requestHeaders(
                                headerWithName("Authorization")
                                        .description("access token")
                                        .attributes(field("constraint", "문자열(jwt)"))
                        )
                ));

        // then
        verify(authService).deleteAccount(anyLong());
    }
}
