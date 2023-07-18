package hanglog.trip.domain;

import static hanglog.global.type.StatusType.USABLE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import hanglog.expense.Expense;
import hanglog.global.BaseEntity;
import hanglog.global.type.StatusType;
import hanglog.trip.domain.type.ItemType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE item SET status = 'DELETED' WHERE id = ?")
@Where(clause = "status = 'USABLE'")
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private ItemType itemType;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    private Integer ordinal;

    @Size(max = 5)
    private Double rating;

    private String memo;

    @ManyToOne(fetch = LAZY, cascade = PERSIST)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = LAZY, cascade = {PERSIST})
    @JoinColumn(name = "day_log_id", nullable = false)
    private DayLog dayLog;

    @OneToOne(fetch = LAZY, cascade = {PERSIST, REMOVE})
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
        this(id, itemType, title, ordinal, rating, memo, place, dayLog, expense, USABLE);
    }

    public Item(
            final Long id,
            final ItemType itemType,
            final String title,
            final Integer ordinal,
            final Double rating,
            final String memo,
            final DayLog dayLog,
            final Expense expense
    ) {
        this(id, itemType, title, ordinal, rating, memo, null, dayLog, expense, USABLE);
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
