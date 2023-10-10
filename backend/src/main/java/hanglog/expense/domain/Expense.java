package hanglog.expense.domain;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import hanglog.category.domain.Category;
import hanglog.global.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE expense SET status = 'DELETED' WHERE id = ?")
@Where(clause = "status = 'USABLE'")
public class Expense extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String currency;

    @Embedded
    private Amount amount;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Expense(
            final Long id,
            final String currency,
            final Amount amount,
            final Category category
    ) {
        this.id = id;
        this.currency = currency;
        this.amount = amount;
        this.category = category;
    }

    public Expense(
            final String currency,
            final Amount amount,
            final Category category
    ) {
        this(null, currency, amount, category);
    }

    public boolean isDifferent(final Expense expense) {
        if (expense == null) {
            return true;
        }
        return !(Objects.equals(currency, expense.currency) && Objects.equals(amount, expense.amount)
                && Objects.equals(category, expense.category));
    }
}
