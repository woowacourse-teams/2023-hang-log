package hanglog.event;

import org.springframework.data.repository.CrudRepository;

public interface RetryEventCountRepository extends CrudRepository<RetryEventCount, Long> {
}
