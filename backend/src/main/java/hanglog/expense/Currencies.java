package hanglog.expense;

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
public class Currencies {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private LocalDate searchDate;

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

    public static Currencies ofDefault() {
        return new Currencies();
    }

    public double getUnitRateOfJpy() {
        return this.jpy / 100;
    }
}
