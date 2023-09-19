package hanglog.trip.service;

import static hanglog.global.exception.ExceptionCode.INVALID_PUBLISHED_STATUS_REQUEST;
import static hanglog.global.exception.ExceptionCode.INVALID_TRIP_WITH_MEMBER;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_CITY_ID;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_MEMBER_ID;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;

import hanglog.city.domain.City;
import hanglog.city.domain.repository.CityRepository;
import hanglog.global.exception.AuthException;
import hanglog.global.exception.BadRequestException;
import hanglog.member.domain.Member;
import hanglog.member.domain.repository.MemberRepository;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.PublishedTrip;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.TripCity;
import hanglog.trip.domain.repository.PublishedTripRepository;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.domain.type.PublishedStatusType;
import hanglog.trip.dto.PublishedStatusRequest;
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

    public void validateTripByMember(final Long memberId, final Long tripId) {
        if (!tripRepository.existsByMemberIdAndId(memberId, tripId)) {
            throw new AuthException(INVALID_TRIP_WITH_MEMBER);
        }
    }

    public Long save(final Long memberId, final TripCreateRequest tripCreateRequest) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID));
        final List<City> cites = tripCreateRequest.getCityIds().stream()
                .map(cityId -> cityRepository.findById(cityId)
                        .orElseThrow(() -> new BadRequestException(NOT_FOUND_CITY_ID)))
                .toList();

        final Trip newTrip = Trip.of(
                member,
                generateInitialTitle(cites),
                tripCreateRequest.getStartDate(),
                tripCreateRequest.getEndDate()
        );
        saveTripCities(cites, newTrip);
        saveDayLogs(newTrip);
        final Trip trip = tripRepository.save(newTrip);
        return trip.getId();
    }

    private void saveTripCities(final List<City> cites, final Trip trip) {
        final List<TripCity> tripCities = cites.stream()
                .map(city -> new TripCity(trip, city))
                .toList();
        tripCityRepository.saveAll(tripCities);
    }

    private void saveDayLogs(final Trip savedTrip) {
        final int days = (int) ChronoUnit.DAYS.between(
                savedTrip.getStartDate(),
                savedTrip.getEndDate().plusDays(1)
        );
        final List<DayLog> dayLogs = IntStream.rangeClosed(1, days + 1)
                .mapToObj(ordinal -> DayLog.generateEmpty(ordinal, savedTrip))
                .toList();
        savedTrip.getDayLogs().addAll(dayLogs);
    }

    public List<TripResponse> getAllTrips(final Long memberId) {
        final List<Trip> trips = tripRepository.findAllByMemberId(memberId);
        return trips.stream()
                .map(this::getTripResponse)
                .toList();
    }

    private TripResponse getTripResponse(final Trip trip) {
        final List<City> cities = getCitiesByTripId(trip.getId());
        return TripResponse.of(trip, cities);
    }

    public TripDetailResponse getTripDetail(final Long tripId) {
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        final List<City> cities = getCitiesByTripId(tripId);
        return TripDetailResponse.of(trip, cities);
    }

    private List<City> getCitiesByTripId(final Long tripId) {
        return tripCityRepository.findByTripId(tripId).stream()
                .map(TripCity::getCity)
                .toList();
    }

    public void update(final Long tripId, final TripUpdateRequest updateRequest) {
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        final List<City> cities = updateRequest.getCityIds().stream()
                .map(cityId -> cityRepository.findById(cityId)
                        .orElseThrow(() -> new BadRequestException(NOT_FOUND_CITY_ID)))
                .toList();

        updateTripCities(tripId, trip, cities);
        updateDayLog(updateRequest, trip);
        trip.update(updateRequest);
        tripRepository.save(trip);
    }

    private void updateTripCities(final Long tripId, final Trip trip, final List<City> cities) {
        // TODO: 전체 삭제 후 지우는 로직 말고 다른 방법으로 리팩토링 필요
        tripCityRepository.deleteAllByTripId(tripId);
        saveTripCities(cities, trip);
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
        trip.getDayLogs().addAll(emptyDayLogs);
    }

    private void removeRemainingDayLogs(final Trip trip, final int currentPeriod, final int requestPeriod) {
        trip.getDayLogs()
                .removeIf(dayLog -> dayLog.getOrdinal() >= requestPeriod + 1 && dayLog.getOrdinal() <= currentPeriod);
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
        validatedPublishedStatusRequest(trip.getPublishedStatus(), publishedStatusRequest);

        final boolean updatedPublishedStatus = publishedStatusRequest.getPublishedStatus();
        if (updatedPublishedStatus) {
            publishTrip(trip);
            return;
        }
        unpublishTrip(trip);
    }

    private void validatedPublishedStatusRequest(
            final PublishedStatusType publishedStatusType,
            final PublishedStatusRequest publishedStatusRequest
    ) {
        final boolean isPublished = publishedStatusRequest.getPublishedStatus();
        final PublishedStatusType updatedPublishedStatus = PublishedStatusType.mappingType(isPublished);
        if (publishedStatusType.equals(updatedPublishedStatus)) {
            throw new BadRequestException(INVALID_PUBLISHED_STATUS_REQUEST);
        }
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
