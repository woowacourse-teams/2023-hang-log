package hanglog.trip.domain.type;

public enum SharedStatusType {

    SHARED,
    UNSHARED;

    public static SharedStatusType mappingType(final boolean isShared) {
        if (isShared) {
            return SHARED;
        }
        return UNSHARED;
    }
}
