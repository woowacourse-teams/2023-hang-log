package hanglog.trip.domain;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import hanglog.category.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Place {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, precision = 16, scale = 13)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 16, scale = 13)
    private BigDecimal longitude;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Place(final String name,
                 final String address,
                 final BigDecimal latitude,
                 final BigDecimal longitude,
                 final Category category
    ) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
    }
}
