package hanglog.event;

import static jakarta.persistence.EnumType.STRING;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanglog.global.BaseEntity;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE outbox SET status = 'DELETED' WHERE id = ?")
@Where(clause = "status = 'USABLE'")
public class Outbox extends BaseEntity {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private EventType eventType;
    @Type(JsonType.class)
    @Column(name = "payload", columnDefinition = "json")
    private String eventPayload;

    public Outbox(final EventType eventType, final String eventPayload) {
        this.id = null;
        this.eventType = eventType;
        this.eventPayload = eventPayload;
    }
}
