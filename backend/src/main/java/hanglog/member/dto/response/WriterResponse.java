package hanglog.member.dto.response;

import static lombok.AccessLevel.PRIVATE;

import hanglog.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class WriterResponse {

    private final String nickname;
    private final String imageUrl;

    public static WriterResponse of(final Member member) {
        return new WriterResponse(member.getNickname(), member.getImageUrl());
    }
}
