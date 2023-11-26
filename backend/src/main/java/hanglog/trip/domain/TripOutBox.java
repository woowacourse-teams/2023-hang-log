package hanglog.trip.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import hanglog.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE trip_outbox SET status = 'DELETED' WHERE id = ?")
@Where(clause = "status = 'USABLE'")
public class TripOutBox extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tripId;

    public TripOutBox(final Long tripId) {
        this.tripId = tripId;
    }
}
