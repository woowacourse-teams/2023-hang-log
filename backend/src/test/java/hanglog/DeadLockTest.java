package hanglog;

import hanglog.city.domain.City;
import hanglog.city.domain.repository.CityRepository;
import hanglog.member.domain.Member;
import hanglog.member.domain.repository.MemberRepository;
import hanglog.trip.dto.request.TripCreateRequest;
import hanglog.trip.service.TripService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeadLockTest {

    @Autowired
    private TripService tripService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CityRepository cityRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(new Member("socialId", "nickname", "image"));
        cityRepository.save(new City(null, "name", "country", BigDecimal.ONE, BigDecimal.ONE));
        cityRepository.save(new City(null, "name", "country", BigDecimal.ONE, BigDecimal.ONE));
    }

    @DisplayName("")
    @Test
    void deadLockTest() throws InterruptedException {
        // given
        List<Long> tripIds = saveTrips();

        // when
        tripIds.stream().parallel().forEach(id -> {
            deleteTrip(id);
        });

        Thread.sleep(20000);
        // then
    }

    private void deleteTrip(Long tripId) {
        tripService.delete(tripId);
    }

    private List<Long> saveTrips() {
        List<Long> tripIds = new ArrayList<>();
        for(int i = 0; i<100; i++) {
            Long tripId = tripService.save(1L, new TripCreateRequest(
                    LocalDate.of(2023, 7, 1),
                    LocalDate.of(2023, 8, 31),
                    List.of(1L, 2L)
            ));
            tripIds.add(tripId);
        }
        return tripIds;
    }

}
