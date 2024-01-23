package hanglog.community.presentation;

import static hanglog.global.restdocs.RestDocsConfiguration.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.global.ControllerTest;
import hanglog.like.dto.request.LikeRequest;
import hanglog.like.presentation.LikeController;
import hanglog.like.service.LikeService;
import hanglog.login.domain.MemberTokens;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(LikeController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class LikeControllerTest extends ControllerTest {

    private static final MemberTokens MEMBER_TOKENS = new MemberTokens("refreshToken", "accessToken");
    private static final Cookie COOKIE = new Cookie("refresh-token", MEMBER_TOKENS.getRefreshToken());

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LikeService likeService;

    @BeforeEach
    void setUp() {
        given(refreshTokenRepository.existsById(any())).willReturn(true);
        doNothing().when(jwtProvider).validateTokens(any());
        given(jwtProvider.getSubject(any())).willReturn("1");
    }

    private ResultActions performPostRequest(final Long tripId, final LikeRequest request) throws Exception {
        return mockMvc.perform(post("/trips/{tripId}/like", tripId)
                .header(AUTHORIZATION, MEMBER_TOKENS.getAccessToken())
                .cookie(COOKIE)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    @DisplayName("게시물의 좋아요 여부를 변경할 수 있다.")
    @Test
    void updateLikeStatus() throws Exception {
        // given
        final LikeRequest likeRequest = new LikeRequest(true);

        doNothing().when(likeService).update(1L, 1L, likeRequest);

        // when
        final ResultActions resultActions = performPostRequest(1L, likeRequest);

        // then
        resultActions.andExpect(status().isNoContent())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("tripId")
                                        .description("여행 ID")
                        ),
                        requestFields(
                                fieldWithPath("isLike")
                                        .type(JsonFieldType.BOOLEAN)
                                        .description("변경할 좋아요 상태값")
                                        .attributes(field("constraint", "True: 좋아요 반영, False: 좋아요 해제"))
                        ))
                );
    }
}
