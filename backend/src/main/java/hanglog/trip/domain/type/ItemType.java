package hanglog.trip.domain.type;

public enum ItemType {

    SPOT(true),
    NON_SPOT(false);

    private final Boolean isSpot;

    ItemType(final boolean isSpot) {
        this.isSpot = isSpot;
    }

    public static ItemType getItemTypeByIsSpot(final boolean isSpot) {
        if (isSpot) {
            return SPOT;
        }
        return NON_SPOT;
    }
}
