package hanglog.trip.service;

import static hanglog.global.exception.ExceptionCode.INVALID_TRIP_WITH_MEMBER;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_CITY_ID;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_MEMBER_ID;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;

import hanglog.city.domain.City;
import hanglog.city.domain.repository.CityRepository;
import hanglog.community.domain.PublishedTrip;
import hanglog.community.domain.type.PublishedStatusType;
import hanglog.global.exception.AuthException;
import hanglog.global.exception.BadRequestException;
import hanglog.member.domain.Member;
import hanglog.member.domain.repository.MemberRepository;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.repository.CustomDayLogRepository;
import hanglog.trip.domain.repository.CustomTripCityRepository;
import hanglog.trip.domain.repository.PublishedTripRepository;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.request.PublishedStatusRequest;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.dto.request.TripUpdateRequest;
import hanglog.trip.dto.response.TripDetailResponse;
import hanglog.trip.dto.response.TripResponse;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TripService {

    private static final String TITLE_POSTFIX = " 여행";

    private final TripRepository tripRepository;
    private final CityRepository cityRepository;
    private final TripCityRepository tripCityRepository;
    private final MemberRepository memberRepository;
    private final PublishedTripRepository publishedTripRepository;
    private final CustomDayLogRepository customDayLogRepository;
    private final CustomTripCityRepository customTripCityRepository;

    public void validateTripByMember(final Long memberId, final Long tripId) {
        if (!tripRepository.existsByMemberIdAndId(memberId, tripId)) {
            throw new AuthException(INVALID_TRIP_WITH_MEMBER);
        }
    }

    public Long save(final Long memberId, final TripCreateRequest tripCreateRequest) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));

        final List<City> cities = cityRepository.findCitiesByIds(tripCreateRequest.getCityIds());
        if (cities.size() != tripCreateRequest.getCityIds().size()) {
            throw new BadRequestException(NOT_FOUND_CITY_ID);
        }

        final Trip newTrip = Trip.of(
                member,
                generateInitialTitle(cities),
                tripCreateRequest.getStartDate(),
                tripCreateRequest.getEndDate()
        );
        final Trip trip = tripRepository.save(newTrip);
        customTripCityRepository.saveAll(cities, trip.getId());
        saveDayLogs(trip);
        return trip.getId();
    }

    private void saveDayLogs(final Trip savedTrip) {
        final int days = (int) ChronoUnit.DAYS.between(
                savedTrip.getStartDate(),
                savedTrip.getEndDate().plusDays(1)
        );
        final List<DayLog> dayLogs = IntStream.rangeClosed(1, days + 1)
                .mapToObj(ordinal -> DayLog.generateEmpty(ordinal, savedTrip))
                .toList();
        customDayLogRepository.saveAll(dayLogs);
    }

    public List<TripResponse> getAllTrips(final Long memberId) {
        final List<Trip> trips = tripRepository.findAllByMemberId(memberId);
        return trips.stream()
                .map(this::getTripResponse)
                .toList();
    }

    private TripResponse getTripResponse(final Trip trip) {
        final List<City> cities = cityRepository.findCitiesByTripId(trip.getId());
        return TripResponse.of(trip, cities);
    }

    public TripDetailResponse getTripDetail(final Long tripId) {
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        final List<City> cities = cityRepository.findCitiesByTripId(tripId);
        return TripDetailResponse.of(trip, cities);
    }

    public void update(final Long tripId, final TripUpdateRequest updateRequest) {
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        final List<City> cities = updateRequest.getCityIds().stream()
                .map(cityId -> cityRepository.findById(cityId)
                        .orElseThrow(() -> new BadRequestException(NOT_FOUND_CITY_ID)))
                .toList();

        updateTripCities(tripId, cities);
        updateDayLog(updateRequest, trip);
        trip.update(updateRequest);
        tripRepository.save(trip);
    }

    private void updateTripCities(final Long tripId, final List<City> cities) {
        // TODO: 전체 삭제 후 지우는 로직 말고 다른 방법으로 리팩토링 필요
        tripCityRepository.deleteAllByTripId(tripId);
        customTripCityRepository.saveAll(cities, tripId);
    }

    private void updateDayLog(final TripUpdateRequest updateRequest, final Trip trip) {
        final int currentPeriod = (int) ChronoUnit.DAYS.between(
                trip.getStartDate(),
                trip.getEndDate().plusDays(1)
        );
        final int requestPeriod = (int) ChronoUnit.DAYS.between(
                updateRequest.getStartDate(),
                updateRequest.getEndDate().plusDays(1)
        );
        if (currentPeriod != requestPeriod) {
            updateDayLogByPeriod(trip, currentPeriod, requestPeriod);
        }
    }

    private void updateDayLogByPeriod(final Trip trip, final int currentPeriod, final int requestPeriod) {
        final DayLog extraDayLog = trip.getDayLogs().get(currentPeriod);
        if (currentPeriod < requestPeriod) {
            addEmptyDayLogs(trip, currentPeriod, requestPeriod);
        }
        if (currentPeriod > requestPeriod) {
            removeRemainingDayLogs(trip, currentPeriod, requestPeriod);
        }
        extraDayLog.updateOrdinal(requestPeriod + 1);
    }

    private void addEmptyDayLogs(final Trip trip, final int currentPeriod, final int requestPeriod) {
        final List<DayLog> emptyDayLogs = IntStream.range(currentPeriod, requestPeriod)
                .mapToObj(ordinal -> DayLog.generateEmpty(ordinal + 1, trip))
                .toList();
        emptyDayLogs.forEach(trip::addDayLog);
    }

    private void removeRemainingDayLogs(final Trip trip, final int currentPeriod, final int requestPeriod) {
        trip.getDayLogs().stream()
                .filter(dayLog -> dayLog.getOrdinal() >= requestPeriod + 1 && dayLog.getOrdinal() <= currentPeriod)
                .forEach(trip::removeDayLog);
    }

    public void delete(final Long tripId) {
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        tripRepository.delete(trip);
        tripCityRepository.deleteAllByTripId(tripId);
        publishedTripRepository.deleteByTripId(tripId);
    }

    private String generateInitialTitle(final List<City> cites) {
        return cites.get(0).getName() + TITLE_POSTFIX;
    }

    public void updatePublishedStatus(final Long tripId, final PublishedStatusRequest publishedStatusRequest) {
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        final boolean isPublished = publishedStatusRequest.getPublishedStatus();
        if (!isChangedPublishedStatus(isPublished, trip)) {
            return;
        }
        if (isPublished) {
            publishTrip(trip);
            return;
        }
        unpublishTrip(trip);
    }

    private boolean isChangedPublishedStatus(final boolean isPublished, final Trip trip) {
        final PublishedStatusType updatedPublishedStatus = PublishedStatusType.mappingType(isPublished);
        return !updatedPublishedStatus.equals(trip.getPublishedStatus());
    }

    private void unpublishTrip(final Trip trip) {
        trip.changePublishedStatus(false);
    }

    private void publishTrip(final Trip trip) {
        trip.changePublishedStatus(true);
        if (!publishedTripRepository.existsByTripId(trip.getId())) {
            final PublishedTrip publishedTrip = new PublishedTrip(trip);
            publishedTripRepository.save(publishedTrip);
        }
    }
}
