package hanglog.member.dto.request;

import hanglog.member.domain.Member;
import hanglog.member.dto.response.MyPageResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MyPageRequest {

    private final String nickname;

    private final String imageUrl;

    public static MyPageResponse from(final Member member) {
        return new MyPageResponse(member.getNickname(), member.getImageUrl());
    }
}
