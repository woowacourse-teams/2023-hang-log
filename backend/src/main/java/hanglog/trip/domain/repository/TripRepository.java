package hanglog.trip.domain.repository;

import hanglog.community.domain.type.PublishedStatusType;
import hanglog.trip.domain.Trip;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TripRepository extends JpaRepository<Trip, Long> {

    boolean existsByMemberIdAndId(final Long memberId, final Long id);

    @Query("SELECT trip FROM Trip trip LEFT JOIN FETCH trip.sharedTrip WHERE trip.id = :tripId")
    Optional<Trip> findTripById(@Param("tripId") final Long id);

    @Query("SELECT trip FROM Trip trip LEFT JOIN FETCH trip.sharedTrip WHERE trip.member.id = :memberId")
    List<Trip> findAllByMemberId(@Param("memberId") final Long memberId);

    @Query("SELECT trip FROM Trip trip "
            + "LEFT JOIN PublishedTrip publishedTrip ON publishedTrip.trip = trip "
            + "WHERE trip.publishedStatus = 'PUBLISHED'")
    List<Trip> findPublishedTripByPageable(final Pageable pageable);

    @Query("SELECT trip FROM Trip trip "
            + "LEFT JOIN Likes likes ON likes.tripId = trip.id "
            + "WHERE trip.publishedStatus = 'PUBLISHED' "
            + "GROUP BY trip.id "
            + "ORDER BY COUNT(likes.tripId) DESC")
    List<Trip> findTripsOrderByLikesCount(final Pageable pageable);

    Long countTripByPublishedStatus(final PublishedStatusType publishedStatusType);

    void deleteAllByMemberId(final Long memberId);

    @Query("""
            SELECT trip
            FROM Trip trip
            LEFT JOIN FETCH trip.sharedTrip sharedTrip
            LEFT JOIN FETCH trip.dayLogs dayLogs
            LEFT JOIN FETCH dayLogs.items items
            LEFT JOIN FETCH items.images images
            LEFT JOIN FETCH items.expense expense
            LEFT JOIN FETCH items.place place
            LEFT JOIN FETCH expense.category expense_category
            LEFT JOIN FETCH place.category place_category
            WHERE dayLogs.trip.id = :tripId
            """)
    Optional<Trip> findById(@Param("tripId") Long tripId);
}
