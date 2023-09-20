package hanglog.member.service;

import static hanglog.global.exception.ExceptionCode.DUPLICATED_MEMBER_NICKNAME;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_MEMBER_ID;

import hanglog.global.exception.BadRequestException;
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
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));
        return MyPageResponse.from(member);
    }

    public void updateMyPageInfo(final Long memberId, final MyPageRequest myPageRequest) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));

        if (member.isNicknameChanged(myPageRequest.getNickname())) {
            checkDuplicatedNickname(myPageRequest.getNickname());
        }

        final Member updateMember = new Member(
                memberId,
                member.getSocialLoginId(),
                myPageRequest.getNickname(),
                myPageRequest.getImageUrl()
        );
        memberRepository.save(updateMember);
    }

    private void checkDuplicatedNickname(final String nickname) {
        if(memberRepository.existsByNickname(nickname)) {
            throw new BadRequestException(DUPLICATED_MEMBER_NICKNAME);
        }
    }
}
