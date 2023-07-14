package hanglog.trip.domain.type;

public enum ItemType {

    SPOT,
    NON_SPOT;

    public static ItemType getItemTypeByIsSpot(final boolean isSpot) {
        if (isSpot) {
            return SPOT;
        }
        return NON_SPOT;
    }
}
