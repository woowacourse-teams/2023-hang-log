package hanglog.integration.controller;

import hanglog.auth.domain.JwtProvider;
import hanglog.auth.domain.MemberTokens;
import hanglog.auth.domain.RefreshToken;
import hanglog.auth.domain.repository.RefreshTokenRepository;
import hanglog.member.domain.Member;
import hanglog.member.domain.repository.MemberRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(value = {
        "classpath:data/truncate.sql",
        "classpath:data/currency.sql",
        "classpath:data/cities.sql",
        "classpath:data/categories.sql"
})
public abstract class IntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtProvider jwtProvider;

    public MemberTokens memberTokens;

    public static String parseUri(final String uri) {
        final String[] parts = uri.split("/");
        return parts[parts.length - 1];
    }

    @BeforeEach
    void setAuth() {
        final Member member = new Member("socialLoginId", "name", "");
        memberRepository.save(member);
        final Long memberId = member.getId();
        memberTokens = jwtProvider.generateLoginToken(memberId.toString());
        final RefreshToken refreshToken = new RefreshToken(memberTokens.getRefreshToken(), memberId);
        refreshTokenRepository.save(refreshToken);
    }

    @BeforeEach
    void setPort() {
        RestAssured.port = port;
    }
}
