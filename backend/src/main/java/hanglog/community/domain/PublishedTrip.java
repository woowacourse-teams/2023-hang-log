package hanglog.community.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import hanglog.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@AllArgsConstructor
@SQLDelete(sql = "UPDATE published_trip SET status = 'DELETED' WHERE id = ?")
@NoArgsConstructor(access = PROTECTED)
@Where(clause = "status = 'USABLE'")
public class PublishedTrip extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tripId;

    public PublishedTrip(final Long tripId) {
        this(null, tripId);
    }
}
