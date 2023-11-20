package hanglog.integration.service;

import hanglog.global.config.RedisTestConfig;
import hanglog.member.domain.Member;
import hanglog.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
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
@Import(RedisTestConfig.class)
public class RedisServiceIntegrationTest {

    @Autowired
    protected MemberRepository memberRepository;

    public Member member;

    @BeforeEach
    void setMember() {
        this.member = memberRepository.save(new Member(
                "socialLoginId",
                "name",
                "https://hanglog.com/img/imageName.png"
        ));
    }
}
