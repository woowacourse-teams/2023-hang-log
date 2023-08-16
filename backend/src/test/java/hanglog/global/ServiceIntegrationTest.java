package hanglog.global;

import hanglog.member.domain.Member;
import hanglog.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql(value = {
        "classpath:data/truncate.sql",
        "classpath:data/currency.sql",
        "classpath:data/cities.sql",
        "classpath:data/categories.sql"
})
public abstract class ServiceIntegrationTest {

    @Autowired
    MemberRepository memberRepository;

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
