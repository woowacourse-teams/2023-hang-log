package hanglog.trip.domain;

import static hanglog.global.exception.ExceptionCode.INVALID_RATING;
import static hanglog.global.type.StatusType.USABLE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import hanglog.expense.domain.Expense;
import hanglog.global.BaseEntity;
import hanglog.global.exception.InvalidDomainException;
import hanglog.global.type.StatusType;
import hanglog.image.domain.Image;
import hanglog.trip.domain.type.ItemType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import java.util.ArrayList;
import java.util.List;
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

    private static final double RATING_DECIMAL_UNIT = 0.5;

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

    @DecimalMax(value = "5.0")
    @DecimalMin(value = "0.5")
    private Double rating;

    private String memo;

    @ManyToOne(fetch = LAZY, cascade = {PERSIST, REMOVE})
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = LAZY, cascade = {PERSIST})
    @JoinColumn(name = "day_log_id", nullable = false)
    private DayLog dayLog;

    @OneToOne(fetch = LAZY, cascade = {PERSIST, REMOVE})
    @JoinColumn(name = "expense_id")
    private Expense expense;

    @OneToMany(mappedBy = "item")
    private List<Image> images = new ArrayList<>();

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
            final List<Image> images,
            final StatusType statusType
    ) {
        super(statusType);
        validateRatingFormat(rating);
        this.id = id;
        this.itemType = itemType;
        this.title = title;
        this.ordinal = ordinal;
        this.rating = rating;
        this.memo = memo;
        this.place = place;
        this.dayLog = dayLog;
        this.expense = expense;
        this.images = images;
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
            final Expense expense,
            final List<Image> images
    ) {
        this(id, itemType, title, ordinal, rating, memo, place, dayLog, expense, images, USABLE);
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
        this(id, itemType, title, ordinal, rating, memo, place, dayLog, expense, new ArrayList<>(), USABLE);
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
        this(id, itemType, title, ordinal, rating, memo, null, dayLog, expense, new ArrayList<>());
    }

    public Item(
            final ItemType itemType,
            final String title,
            final Integer ordinal,
            final Double rating,
            final String memo,
            final Place place,
            final DayLog dayLog,
            final Expense expense,
            final List<Image> images
    ) {
        this(null, itemType, title, ordinal, rating, memo, place, dayLog, expense, images);
    }

    private void validateRatingFormat(final Double rating) {
        if (rating % RATING_DECIMAL_UNIT != 0) {
            throw new InvalidDomainException(INVALID_RATING);
        }
    }

    public void changeOrdinal(final int ordinal) {
        this.ordinal = ordinal;
    }
}
