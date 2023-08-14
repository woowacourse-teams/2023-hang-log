package hanglog.member.service;

import hanglog.global.exception.BadRequestException;
import hanglog.global.exception.ExceptionCode;
import hanglog.member.domain.Member;
import hanglog.member.domain.repository.MemberRepository;
import hanglog.member.dto.request.MyPageRequest;
import hanglog.member.dto.response.MyPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MyPageResponse getMyPageInfo(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_MEMBER_ID));
        return MyPageResponse.from(member);
    }

    public void updateMyPageInfo(final Long memberId, final MyPageRequest myPageRequest) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_MEMBER_ID));
        final Member updateMember = new Member(
                memberId,
                member.getSocialLoginId(),
                myPageRequest.getNickname(),
                myPageRequest.getImageUrl()
        );
        memberRepository.save(updateMember);
    }
}
