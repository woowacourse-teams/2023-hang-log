package hanglog.like.domain;

import java.time.Duration;

public class LikeRedisConstants {

    public static final String LIKE_KEY_PREFIX = "like:";
    public static final String WILD_CARD = "*";
    public static final String KEY_SEPARATOR = ":";
    public static final Long EMPTY_MARKER = -1L;
    public static final Duration LIKE_TTL = Duration.ofMinutes(90L);

    public static String generateLikeKey(final Long tripId) {
        return LIKE_KEY_PREFIX + tripId;
    }
}
