package hanglog.city.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import hanglog.city.dto.request.CityRequest;
import hanglog.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class City extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false, precision = 16, scale = 13)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 16, scale = 13)
    private BigDecimal longitude;

    public static City of(final CityRequest cityRequest) {
        return new City(
                null,
                cityRequest.getName(),
                cityRequest.getCountry(),
                cityRequest.getLatitude(),
                cityRequest.getLongitude()
        );
    }

    public void update(final CityRequest cityRequest) {
        this.name = cityRequest.getName();
        this.country = cityRequest.getCountry();
        this.latitude = cityRequest.getLatitude();
        this.longitude = cityRequest.getLongitude();
    }

    public boolean isSameNameAndCountry(final String name, final String country) {
        return this.name.equals(name) && this.country.equals(country);
    }
}
