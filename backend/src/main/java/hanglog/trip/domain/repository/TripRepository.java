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

}
