package hanglog.currency.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Currency {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalDate date;

    @Column(nullable = false)
    private Double usd;

    @Column(nullable = false)
    private Double eur;

    @Column(nullable = false)
    private Double gbp;

    @Column(nullable = false)
    private Double jpy;

    @Column(nullable = false)
    private Double cny;

    @Column(nullable = false)
    private Double chf;

    @Column(nullable = false)
    private Double sgd;

    @Column(nullable = false)
    private Double thb;

    @Column(nullable = false)
    private Double hkd;

    @Column(nullable = false)
    private Double krw;

    public Currency(
            final LocalDate date,
            final Double usd,
            final Double eur,
            final Double gbp,
            final Double jpy,
            final Double cny,
            final Double chf,
            final Double sgd,
            final Double thb,
            final Double hkd,
            final Double krw
    ) {
        this(null, date, usd, eur, gbp, jpy, cny, chf, sgd, thb, hkd, krw);
    }

    public double getUnitRateOfJpy() {
        return this.jpy / 100;
    }
}
