package hanglog.trip.domain;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import hanglog.trip.domain.type.ItemType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Item {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private ItemType itemType;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long rating;

    private String memo;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "day_log_id", nullable = false)
    private DayLog dayLog;

    public Item(final ItemType itemType,
                final String title,
                final Long rating,
                final String memo,
                final Place place,
                final DayLog dayLog
    ) {
        this.itemType = itemType;
        this.title = title;
        this.rating = rating;
        this.memo = memo;
        this.place = place;
        this.dayLog = dayLog;
    }
}
