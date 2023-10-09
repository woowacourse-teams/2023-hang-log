package hanglog.trip.domain.repository;

import hanglog.trip.domain.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Modifying
    @Query("""
                UPDATE Item item
                SET item.status = 'DELETED'
                WHERE item.id IN :itemIds
            """)
    void deleteByIds(@Param("itemIds") final List<Long> itemIds);
}
