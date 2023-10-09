package hanglog.member.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberDeleteEvent {

    private final Long memberId;
}
