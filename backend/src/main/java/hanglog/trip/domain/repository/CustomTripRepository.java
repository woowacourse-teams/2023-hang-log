package hanglog.trip.domain.repository;

import java.util.List;

public interface CustomTripRepository {

    List<Long> findTripIdsByMemberId(final Long memberId);
}
