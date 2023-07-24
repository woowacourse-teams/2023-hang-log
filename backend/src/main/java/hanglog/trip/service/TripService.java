package hanglog.trip.service;

import static hanglog.global.exception.ExceptionCode.NOT_FOUND_CITY_ID;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_TRIP_ID;

import hanglog.global.exception.BadRequestException;
import hanglog.trip.domain.City;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.TripCity;
import hanglog.trip.domain.repository.CityRepository;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.dto.request.TripUpdateRequest;
import hanglog.trip.dto.response.TripResponse;
import java.time.Period;
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

    public Long save(final TripCreateRequest tripCreateRequest) {
        final List<City> cites = tripCreateRequest.getCityIds().stream()
                .map(cityId -> cityRepository.findById(cityId)
                        .orElseThrow(() -> new BadRequestException(NOT_FOUND_CITY_ID)))
                .toList();

        final Trip trip = new Trip(getInitTitle(cites), tripCreateRequest.getStartDate(),
                tripCreateRequest.getEndDate());
        final Trip savedTrip = tripRepository.save(trip);
        saveAllTripCities(cites, savedTrip);
        saveDayLogs(savedTrip);
        tripRepository.save(savedTrip);
        return savedTrip.getId();
    }

    private void saveDayLogs(final Trip savedTrip) {
        final Period period = Period.between(savedTrip.getStartDate(), savedTrip.getEndDate());
        final List<DayLog> dayLogs = IntStream.range(1, period.getDays() + 1)
                .mapToObj(ordinal -> DayLog.generateEmpty(ordinal, savedTrip))
                .toList();
        savedTrip.getDayLogs().addAll(dayLogs);
    }

    public List<TripResponse> getAllTrip() {
        final List<Trip> trips = tripRepository.findAll();
        return trips.stream()
                .map(TripResponse::of)
                .toList();
    }

    public TripResponse getTrip(final Long tripId) {
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        return TripResponse.of(trip);
    }

    public void update(final Long tripId, final TripUpdateRequest updateRequest) {
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        final int currentPeriod = Period.between(trip.getStartDate(), trip.getEndDate()).getDays() + 1;
        final int requestPeriod =
                Period.between(updateRequest.getStartDate(), updateRequest.getEndDate()).getDays() + 1;

        if (currentPeriod != requestPeriod) {
            changePeriod(trip, currentPeriod, requestPeriod);
        }

        final Trip updatedTrip = new Trip(
                trip.getId(),
                trip.getTitle(),
                updateRequest.getStartDate(),
                updateRequest.getEndDate(),
                updateRequest.getDescription(),
                trip.getDayLogs()
        );
        tripRepository.save(updatedTrip);
    }

    private void changePeriod(final Trip trip, final int currentPeriod, final int requestPeriod) {
        final DayLog extraDayLog = trip.getDayLogs().remove(currentPeriod);
        extraDayLog.updateOrdinal(requestPeriod + 1);

        if (currentPeriod < requestPeriod) {
            IntStream.range(currentPeriod, requestPeriod)
                    .mapToObj(i -> DayLog.generateEmpty(i + 1, trip))
                    .forEach(trip.getDayLogs()::add);
        }

        if (currentPeriod > requestPeriod) {
            trip.getDayLogs().removeIf(dayLog ->
                    dayLog.getOrdinal() >= requestPeriod + 1 && dayLog.getOrdinal() <= currentPeriod
            );
        }
        trip.getDayLogs().add(extraDayLog);
    }

    public void delete(final Long tripId) {
        final Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRIP_ID));
        tripRepository.delete(trip);
    }

    private String getInitTitle(final List<City> cites) {
        return cites.get(0).getName() + TITLE_POSTFIX;
    }

    private void saveAllTripCities(final List<City> cites, final Trip trip) {
        final List<TripCity> tripCities = cites.stream()
                .map(city -> new TripCity(trip, city))
                .toList();
        tripCityRepository.saveAll(tripCities);
    }
}
