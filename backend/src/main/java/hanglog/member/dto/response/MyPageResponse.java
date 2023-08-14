package hanglog.member.dto.response;

import hanglog.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MyPageResponse {

    private final String nickname;

    private final String imageUrl;

    public static MyPageResponse from(final Member member) {
        return new MyPageResponse(member.getNickname(), member.getImageUrl());
    }
}
