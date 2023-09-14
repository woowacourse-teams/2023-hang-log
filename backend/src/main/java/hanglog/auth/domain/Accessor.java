package hanglog.auth.domain;

import static hanglog.auth.domain.Authority.MEMBER;

import lombok.Getter;

@Getter
public class Accessor {

    private final Long memberId;
    private final Authority authority;

    private Accessor(final Long memberId, final Authority authority) {
        this.memberId = memberId;
        this.authority = authority;
    }

    public static Accessor guest() {
        return new Accessor(0L, Authority.GUEST);
    }

    public static Accessor member(final Long memberId) {
        return new Accessor(memberId, MEMBER);
    }

    public boolean isMember() {
        return MEMBER.equals(authority);
    }
}
