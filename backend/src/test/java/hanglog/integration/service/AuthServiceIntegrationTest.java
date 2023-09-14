package hanglog.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import hanglog.auth.domain.BearerAuthorizationExtractor;
import hanglog.auth.domain.JwtProvider;
import hanglog.auth.domain.oauthprovider.OauthProviders;
import hanglog.auth.service.AuthService;
import hanglog.member.domain.Member;
import hanglog.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(value = {
        "classpath:data/truncate.sql",
        "classpath:data/currency.sql",
        "classpath:data/cities.sql",
        "classpath:data/categories.sql"
})
@Import({
        AuthService.class,
        OauthProviders.class,
        JwtProvider.class,
        BearerAuthorizationExtractor.class,
})
class AuthServiceIntegrationTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    public Member member;

    @BeforeEach
    void setMember() {
        this.member = memberRepository.save(new Member(
                "socialLoginId",
                "name",
                "https://hanglog.com/img/imageName.png"
        ));
    }

    @DisplayName("멤버를 삭제한다.")
    @Test
    void deleteAccount() {
        // when & then
        assertThat(memberRepository.findById(member.getId())).isPresent();
        assertDoesNotThrow(() -> authService.deleteAccount(member.getId()));
        assertThat(memberRepository.findById(member.getId())).isNotPresent();
    }
}
