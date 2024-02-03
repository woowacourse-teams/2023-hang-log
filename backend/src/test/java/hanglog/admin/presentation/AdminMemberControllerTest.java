package hanglog.admin.presentation;

import static hanglog.admin.domain.type.AdminType.MASTER;
import static hanglog.global.restdocs.RestDocsConfiguration.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.admin.domain.AdminMember;
import hanglog.admin.domain.type.AdminType;
import hanglog.admin.dto.request.AdminMemberCreateRequest;
import hanglog.admin.dto.request.PasswordUpdateRequest;
import hanglog.admin.dto.response.AdminMemberResponse;
import hanglog.admin.service.AdminMemberService;
import hanglog.global.ControllerTest;
import hanglog.login.domain.MemberTokens;
import jakarta.servlet.http.Cookie;
import java.util.List;
import java.util.Optional;
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

@WebMvcTest(AdminMemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class AdminMemberControllerTest extends ControllerTest {

    private static final MemberTokens MEMBER_TOKENS = new MemberTokens("refreshToken", "accessToken");
    private static final Cookie COOKIE = new Cookie("refresh-token", MEMBER_TOKENS.getRefreshToken());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminMemberService adminMemberService;

    @BeforeEach
    void setUp() {
        given(refreshTokenRepository.existsById(any())).willReturn(true);
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
        given(adminMemberRepository.findById(1L)).willReturn(Optional.of(new AdminMember(
                1L,
                "username",
                "password",
                MASTER
        )));
    }

    @DisplayName("관리자 멤버 목록을 조회한다.")
    @Test
    void getAdminMembers() throws Exception {
        // given
        final AdminMember adminMember = new AdminMember(1L, "adminUser", "password", AdminType.MASTER);
        final AdminMemberResponse response = AdminMemberResponse.from(adminMember);

        given(adminMemberService.getAdminMembers()).willReturn(List.of(response));

        // when & then
        mockMvc.perform(get("/admin/members")
                        .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                        .cookie(COOKIE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(response.getId()))
                .andExpect(jsonPath("$[0].username").value(response.getUsername()))
                .andExpect(jsonPath("$[0].adminType").value(response.getAdminType()))
                .andDo(restDocs.document(
                        responseFields(
                                fieldWithPath("[].id")
                                        .type(JsonFieldType.NUMBER)
                                        .description("멤버 ID")
                                        .attributes(field("constraint", "양의 정수")),
                                fieldWithPath("[].username")
                                        .type(JsonFieldType.STRING)
                                        .description("사용자 이름")
                                        .attributes(field("constraint", "20자 이내의 문자열")),
                                fieldWithPath("[].adminType")
                                        .type(JsonFieldType.STRING)
                                        .description("관리자 유형")
                                        .attributes(field("constraint", "ADMIN / MASTER"))
                        )
                ));
    }

    @DisplayName("새로운 관리자 멤버를 생성한다.")
    @Test
    void createAdminMember() throws Exception {
        // given
        final AdminMemberCreateRequest request = new AdminMemberCreateRequest(
                "newAdmin",
                "password",
                "ADMIN"
        );
        given(adminMemberService.createAdminMember(any(AdminMemberCreateRequest.class))).willReturn(2L);

        // when & then
        mockMvc.perform(post("/admin/members")
                        .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                        .cookie(COOKIE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/admin/members/2"))
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("username")
                                        .type(JsonFieldType.STRING)
                                        .description("사용자 이름")
                                        .attributes(field("constraint", "20자 이내의 문자열")),
                                fieldWithPath("password")
                                        .type(JsonFieldType.STRING)
                                        .description("비밀번호")
                                        .attributes(field("constraint", "4자이상 20자 이하의 문자열")),
                                fieldWithPath("adminType")
                                        .type(JsonFieldType.STRING)
                                        .description("관리자 유형")
                                        .attributes(field("constraint", "4자이상 20자 이하의 문자열"))
                        )
                ));
    }

    @DisplayName("관리자 멤버의 비밀번호를 업데이트한다.")
    @Test
    void updatePassword() throws Exception {
        // given
        final PasswordUpdateRequest request = new PasswordUpdateRequest("oldPassword", "newPassword");
        doNothing().when(adminMemberService).updatePassword(1L, request);

        // when & then
        mockMvc.perform(patch("/admin/members/{memberId}/password", 1)
                        .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                        .cookie(COOKIE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("memberId")
                                        .description("관리자 멤버 ID")
                        ),
                        requestFields(
                                fieldWithPath("currentPassword")
                                        .type(JsonFieldType.STRING)
                                        .description("현재 비밀번호")
                                        .attributes(field("constraint", "4자이상 20자 이하의 문자열")),
                                fieldWithPath("newPassword")
                                        .type(JsonFieldType.STRING)
                                        .description("새 비밀번호")
                                        .attributes(field("constraint", "4자이상 20자 이하의 문자열"))
                        )
                ));
    }
}
