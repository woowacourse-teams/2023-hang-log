package hanglog.trip.domain.type;

import java.util.Arrays;

public enum ItemType {

    SPOT(true),
    NON_SPOT(false);

    private final Boolean isSpot;

    ItemType(final boolean isSpot) {
        this.isSpot = isSpot;
    }

    public static ItemType getItemTypeByIsSpot(boolean isSpot) {
        return Arrays.stream(ItemType.values()).filter(
                itemType -> itemType.isSpot.equals(isSpot)
        ).findFirst().get();
    }
}
