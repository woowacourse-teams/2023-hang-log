package hanglog.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import hanglog.member.domain.Member;
import hanglog.member.domain.repository.MemberRepository;
import hanglog.member.dto.MyPageResponse;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @DisplayName("")
    @Test
    void getMyPageInfo() {
        // given
        final Member member = new Member(1L, "jjongwa", "dino", "goodDino'sImageUrl");
        given(memberRepository.save(any()))
                .willReturn(member);
        given(memberRepository.findById(member.getId()))
                .willReturn(Optional.of(member));

        // when
        final MyPageResponse actual = memberService.getMyPageInfo(member.getId());

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(MyPageResponse.from(member));
    }

}
