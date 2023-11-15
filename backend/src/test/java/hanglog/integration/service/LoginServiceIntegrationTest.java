package hanglog.integration.service;

import static org.assertj.core.api.Assertions.assertThat;

import hanglog.login.service.LoginService;
import hanglog.member.domain.Member;
import hanglog.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(value = {"classpath:data/truncate.sql"})
class LoginServiceIntegrationTest {

    @Autowired
    private LoginService loginService;
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("멤버를 삭제한다.")
    @Test
    void deleteAccount() {
        // given
        final Member member = memberRepository.save(new Member(
                "socialLoginId",
                "name",
                "https://hanglog.com/img/imageName.png"
        ));
        assertThat(memberRepository.findById(member.getId())).isPresent();

        // when
        loginService.deleteAccount(member.getId());

        // then
        assertThat(memberRepository.findById(member.getId())).isEmpty();
    }
}
