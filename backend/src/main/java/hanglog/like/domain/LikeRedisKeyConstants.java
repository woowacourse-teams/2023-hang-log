package hanglog.like.domain;

public class LikeRedisKeyConstants {

    public static final String LIKE_KEY_PREFIX = "like:";
    public static final String SEPARATOR = ":";
    public static final String EMPTY_MARKER = "empty";

    public static String generateLikeKey(final Long tripId) {
        return LIKE_KEY_PREFIX + tripId;
    }
}
