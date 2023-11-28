package hanglog.outbox.domain;

import static hanglog.outbox.domain.type.TargetType.MEMBER;
import static hanglog.outbox.domain.type.TargetType.TRIP;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import hanglog.global.BaseEntity;
import hanglog.outbox.domain.type.TargetType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
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
public class OutBox extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long targetId;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private TargetType targetType;

    public OutBox(final Long targetId, final TargetType targetType) {
        this.targetId = targetId;
        this.targetType = targetType;
    }

    public boolean isMember() {
        return this.targetType.equals(MEMBER);
    }

    public boolean isTrip() {
        return this.targetType.equals(TRIP);
    }
}
