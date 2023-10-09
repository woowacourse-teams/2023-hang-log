package hanglog.member.event;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberDeleteEvent {

    private final List<Long> tripIds;
    private final Long memberId;
}
