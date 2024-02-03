package hanglog.integration.controller;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import hanglog.admin.domain.type.AdminType;
import hanglog.admin.dto.request.AdminMemberCreateRequest;
import hanglog.login.domain.MemberTokens;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class AdminMemberIntegrationTest extends AdminIntegrationTest {

    @DisplayName("마스터 계정은 어드민 계정을 생성할 수 있다.")
    @Test
    void createAdminMember() {
        // given
        final MemberTokens masterMemberToken = getAdminMemberTokenBy(AdminType.MASTER);
        final AdminMemberCreateRequest createRequest = new AdminMemberCreateRequest("admin", "password", "ADMIN");

        // when
        final ExtractableResponse<Response> response = requestCreateAdminMember(masterMemberToken, createRequest);
        final Long adminId = Long.parseLong(parseUri(response.header("Location")));

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
                    softly.assertThat(response.header("Location")).isNotBlank();
                    softly.assertThat(adminId).isPositive();
                }
        );
    }

    public static ExtractableResponse<Response> requestCreateAdminMember(final MemberTokens memberTokens,
                                                                         final AdminMemberCreateRequest createRequest) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION,
                        "Bearer " + memberTokens.getAccessToken())
                .cookies("refresh-token", memberTokens.getRefreshToken())
                .contentType(JSON)
                .body(createRequest)
                .when().post("/admin/members")
                .then().log().all()
                .extract();
    }
}
