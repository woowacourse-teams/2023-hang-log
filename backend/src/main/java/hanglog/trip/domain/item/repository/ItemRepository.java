package hanglog.trip.domain.item.repository;

import hanglog.trip.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
