package hanglog.member.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MyPageRequest {

    private final String nickname;

    private final String imageUrl;
}
