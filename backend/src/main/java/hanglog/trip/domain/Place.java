package hanglog.trip.domain;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import hanglog.category.Category;
import hanglog.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE place SET status = 'DELETED' WHERE id = ?")
@Where(clause = "status = 'USABLE'")
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 16, scale = 13)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 16, scale = 13)
    private BigDecimal longitude;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Place(
            final String name,
            final BigDecimal latitude,
            final BigDecimal longitude,
            final Category category
    ) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
    }
}
