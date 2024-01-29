package hanglog.event;

public enum EventType {

    TRIP_DELETE(1),
    MEMBER_DELETE(2);

    private final int number;

    EventType(final int number) {
        this.number = number;
    }
}
