package hanglog.event;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash(value = "retryEventCount")
public class RetryEventCount {

    @Id
    private Long eventId;
    private Integer count;

    public void increase() {
        count += 1;
    }

    public Boolean isMax() {
        return count > 3;
    }
}
