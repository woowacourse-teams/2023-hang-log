package hanglog.trip.fixture;

import static hanglog.integration.IntegrationFixture.MEMBER;
import static hanglog.trip.fixture.DayLogFixture.EXPENSE_JAPAN_DAYLOG;
import static hanglog.trip.fixture.DayLogFixture.EXPENSE_LONDON_DAYLOG;
import static hanglog.trip.fixture.DayLogFixture.LONDON_DAYLOG_1;
import static hanglog.trip.fixture.DayLogFixture.LONDON_DAYLOG_2;
import static hanglog.trip.fixture.DayLogFixture.LONDON_DAYLOG_EXTRA;

import hanglog.community.domain.type.PublishedStatusType;
import hanglog.share.domain.type.SharedStatusType;
import hanglog.trip.domain.Trip;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class TripFixture {

    public static final Trip LONDON_TRIP = new Trip(
            1L,
            MEMBER,
            "런던 여행",
            "default-image.png",
            LocalDate.of(2023, 7, 1),
            LocalDate.of(2023, 7, 2),
            "",
            null,
            new ArrayList<>(List.of(LONDON_DAYLOG_1, LONDON_DAYLOG_2, LONDON_DAYLOG_EXTRA)),
            SharedStatusType.UNSHARED,
            PublishedStatusType.UNPUBLISHED
    );

    public static final Trip LONDON_TO_JAPAN = new Trip(
            1L,
            MEMBER,
            "런던에서 일본으로",
            "default-image.png",
            LocalDate.of(2023, 7, 1),
            LocalDate.of(2023, 7, 2),
            "",
            null,
            List.of(EXPENSE_LONDON_DAYLOG, EXPENSE_JAPAN_DAYLOG),
            SharedStatusType.UNSHARED,
            PublishedStatusType.UNPUBLISHED
    );
}
