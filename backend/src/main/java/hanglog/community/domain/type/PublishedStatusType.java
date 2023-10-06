package hanglog.community.domain.type;

public enum PublishedStatusType {

    PUBLISHED,
    UNPUBLISHED;

    public static PublishedStatusType mappingType(final boolean isPublished) {
        if (isPublished) {
            return PUBLISHED;
        }
        return UNPUBLISHED;
    }
}
