package hanglog.community.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Likes {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JoinColumn(name = "trip_id", nullable = false, unique = true)
    private Long tripId;

    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Long memberId;

    public Likes(final Long tripId, final Long memberId) {
        this.tripId = tripId;
        this.memberId = memberId;
    }
}
