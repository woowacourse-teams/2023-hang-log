package hanglog.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import hanglog.member.domain.Member;
import hanglog.member.domain.repository.MemberRepository;
import hanglog.member.dto.request.MyPageRequest;
import hanglog.member.dto.response.MyPageResponse;
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

    @DisplayName("멤버의 닉네임과 프로필 사진을 조회할 수 있다.")
    @Test
    void getMyPageInfo() {
        // given
        final Member member = new Member(1L, "jjongwa", "dino", "https://hanglog.com/img/imageName.png");
        given(memberRepository.findById(member.getId()))
                .willReturn(Optional.of(member));

        // when
        final MyPageResponse actual = memberService.getMyPageInfo(member.getId());

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(MyPageResponse.from(member));
    }

    @DisplayName("멤버의 닉네임과 프로필 사진을 수정할 수 있다.")
    @Test
    void updateMyPageInfo() {
        // given
        final MyPageRequest myPageRequest = new MyPageRequest("badDino", "https://hanglog.com/img/imageName.png");
        final Member member = new Member(1L, "jjongwa", "goodDino", "https://hanglog.com/img/imageName.png");
        final Member updatedMember = new Member(
                1L,
                "jjongwa",
                myPageRequest.getNickname(),
                myPageRequest.getImageUrl()
        );
        given(memberRepository.findById(any()))
                .willReturn(Optional.of(member));
        given(memberRepository.save(any()))
                .willReturn(updatedMember);

        // when
        memberService.updateMyPageInfo(member.getId(), myPageRequest);

        // then
        verify(memberRepository).findById(any());
        verify(memberRepository).save(any());
    }

}
