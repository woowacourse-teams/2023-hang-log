package hanglog.integration.controller;


import hanglog.admin.domain.AdminMember;
import hanglog.admin.domain.repository.AdminMemberRepository;
import hanglog.admin.domain.type.AdminType;
import hanglog.global.config.RedisTestConfig;
import hanglog.login.domain.MemberTokens;
import hanglog.login.domain.RefreshToken;
import hanglog.login.domain.repository.RefreshTokenRepository;
import hanglog.login.infrastructure.JwtProvider;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(value = {
        "classpath:data/truncate.sql",
        "classpath:data/currency.sql",
        "classpath:data/cities.sql",
        "classpath:data/categories.sql"
})
@Import({
        RedisTestConfig.class
})
public abstract class AdminIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    private AdminMemberRepository adminMemberRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtProvider jwtProvider;

    public static String parseUri(final String uri) {
        final String[] parts = uri.split("/");
        return parts[parts.length - 1];
    }

    public MemberTokens getAdminMemberTokenBy(final AdminType adminType) {
        final AdminMember adminMember = new AdminMember("username", "password", adminType);
        adminMemberRepository.save(adminMember);
        final Long memberId = adminMember.getId();
        final MemberTokens memberToken = jwtProvider.generateLoginToken(memberId.toString());
        final RefreshToken refreshToken = new RefreshToken(memberToken.getRefreshToken(), memberId);
        refreshTokenRepository.save(refreshToken);

        return memberToken;
    }

    @BeforeEach
    void setPort() {
        RestAssured.port = port;
    }
}
