package hanglog.member.service;

import static hanglog.global.exception.ExceptionCode.DUPLICATED_MEMBER_NICKNAME;
import static hanglog.global.exception.ExceptionCode.INVALID_IMAGE_URL;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_MEMBER_ID;

import hanglog.global.exception.BadRequestException;
import hanglog.image.domain.S3ImageEvent;
import hanglog.member.domain.Member;
import hanglog.member.domain.repository.MemberRepository;
import hanglog.member.dto.request.MyPageRequest;
import hanglog.member.dto.response.MyPageResponse;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private static final String IMAGE_URL_HOST = "image.hanglog.com";

    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher publisher;

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
        deleteOriginalImage(member.getImageUrl(), updateMember.getImageUrl());
        memberRepository.save(updateMember);
    }

    private void checkDuplicatedNickname(final String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new BadRequestException(DUPLICATED_MEMBER_NICKNAME);
        }
    }

    private void deleteOriginalImage(final String originalUrl, final String updatedUrl) {
        if (originalUrl.equals(updatedUrl)) {
            return;
        }
        try {
            final URL targetUrl = new URL(originalUrl);
            if (targetUrl.getHost().equals(IMAGE_URL_HOST)) {
                final String targetName = originalUrl.substring(originalUrl.lastIndexOf("/") + 1);
                publisher.publishEvent(new S3ImageEvent(targetName));
            }
        } catch (final MalformedURLException e) {
            throw new BadRequestException(INVALID_IMAGE_URL);
        }
    }
}
