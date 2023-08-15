package hanglog.member.controller;

import static hanglog.trip.restdocs.RestDocsConfiguration.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.auth.domain.MemberTokens;
import hanglog.global.ControllerTest;
import hanglog.member.dto.request.MyPageRequest;
import hanglog.member.dto.response.MyPageResponse;
import hanglog.member.presentation.MemberController;
import hanglog.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class MemberControllerTest extends ControllerTest {

    private final static String REFRESH_TOKEN = "refreshToken";
    private final static String ACCESS_TOKEN = "accessToken";

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
    }

    @DisplayName("사용자 정보를 조회할 수 있다.")
    @Test
    void getMyInfo() throws Exception {
        // given
        final MemberTokens memberTokens = new MemberTokens(REFRESH_TOKEN, ACCESS_TOKEN);
        final Cookie cookie = new Cookie("refresh-token", memberTokens.getRefreshToken());
        final MyPageResponse myPageResponse = new MyPageResponse("dino", "https://hanglog.com/img/imageName.png");
        when(memberService.getMyPageInfo(anyLong()))
                .thenReturn(myPageResponse);

        final ResultActions resultActions = mockMvc.perform(get("/mypage")
                .header(HttpHeaders.AUTHORIZATION, ACCESS_TOKEN)
                .cookie(cookie)
        );

        // when
        resultActions.andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName("Authorization")
                                        .description("access token")
                                        .attributes(field("constraint", "문자열(jwt)"))
                        ),
                        requestCookies(
                                cookieWithName("refresh-token")
                                        .description("갱신 토큰")
                        ),
                        responseFields(
                                fieldWithPath("nickname")
                                        .type(STRING)
                                        .description("멤버 닉네임")
                                        .attributes(field("constraint", "문자열")),
                                fieldWithPath("imageUrl")
                                        .type(STRING)
                                        .description("멤버 프로필사진 URL")
                                        .attributes(field("constraint", "문자열"))
                        )
                ));

        // then
        verify(memberService).getMyPageInfo(anyLong());
    }

    @DisplayName("사용자 정보를 수정할 수 있다.")
    @Test
    void updateMyInfo() throws Exception {
        // given
        final MemberTokens memberTokens = new MemberTokens(REFRESH_TOKEN, ACCESS_TOKEN);
        final Cookie cookie = new Cookie("refresh-token", memberTokens.getRefreshToken());
        final MyPageRequest request = new MyPageRequest("badDino", "https://hanglog.com/img/imageName2.png");

        doNothing().when(memberService).updateMyPageInfo(anyLong(), any());

        final ResultActions resultActions = mockMvc.perform(put("/mypage")
                .header(HttpHeaders.AUTHORIZATION, ACCESS_TOKEN)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .cookie(cookie)
        );

        // when
        resultActions.andExpect(status().isNoContent())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName("Authorization")
                                        .description("access token")
                                        .attributes(field("constraint", "문자열(jwt)"))
                        ),
                        requestCookies(
                                cookieWithName("refresh-token")
                                        .description("갱신 토큰")
                        ),
                        requestFields(
                                fieldWithPath("nickname")
                                        .type(STRING)
                                        .description("멤버 닉네임")
                                        .attributes(field("constraint", "문자열")),
                                fieldWithPath("imageUrl")
                                        .type(STRING)
                                        .description("멤버 프로필사진 URL")
                                        .attributes(field("constraint", "문자열"))
                        )
                ));

        // then
        verify(memberService).updateMyPageInfo(anyLong(), any());
    }
}
