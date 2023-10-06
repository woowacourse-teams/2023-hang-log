package hanglog.community.domain;

public class BaseTripInfo implements TripInfo {

    @Override
    public Long getLikeCount() {
        return 0L;
    }

    @Override
    public Boolean getIsLike() {
        return false;
    }
}
