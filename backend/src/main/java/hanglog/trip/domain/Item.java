package hanglog.trip.domain;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import hanglog.expense.Expense;
import hanglog.global.BaseEntity;
import hanglog.global.type.StatusType;
import hanglog.trip.domain.type.ItemType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private ItemType itemType;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer ordinal;

    @Column(nullable = false)
    private Double rating;

    private String memo;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "day_log_id", nullable = false)
    private DayLog dayLog;

    @OneToOne(fetch = LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "expense_id")
    private Expense expense;

    public Item(
            final Long id,
            final ItemType itemType,
            final String title,
            final Integer ordinal,
            final Double rating,
            final String memo,
            final Place place,
            final DayLog dayLog,
            final Expense expense,
            final StatusType statusType
    ) {
        super(statusType);
        this.id = id;
        this.itemType = itemType;
        this.title = title;
        this.ordinal = ordinal;
        this.rating = rating;
        this.memo = memo;
        this.place = place;
        this.dayLog = dayLog;
        this.expense = expense;
        if (!dayLog.getItems().contains(this)) {
            dayLog.getItems().add(this);
        }
    }

    public Item(
            final Long id,
            final ItemType itemType,
            final String title,
            final Integer ordinal,
            final Double rating,
            final String memo,
            final Place place,
            final DayLog dayLog,
            final Expense expense
    ) {
        this(id, itemType, title, ordinal, rating, memo, place, dayLog, expense, StatusType.USABLE);
    }

    public Item(
            final ItemType itemType,
            final String title,
            final Integer ordinal,
            final Double rating,
            final String memo,
            final Place place,
            final DayLog dayLog,
            final Expense expense
    ) {
        this(null, itemType, title, ordinal, rating, memo, place, dayLog, expense);
    }
}
