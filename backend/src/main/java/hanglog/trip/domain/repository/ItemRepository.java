package hanglog.trip.domain.repository;

import hanglog.trip.domain.Item;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("""
            SELECT item
            FROM Item item
            LEFT JOIN FETCH item.images images
            LEFT JOIN FETCH item.expense expense
            LEFT JOIN FETCH item.place place
            LEFT JOIN FETCH expense.category expense_category
            LEFT JOIN FETCH place.category place_category
            WHERE item.id = :itemId
            """)
    Optional<Item> findById(@Param("itemId") final Long itemId);

    @Modifying
    @Query("""
            UPDATE Item item
            SET item.status = 'DELETED'
            WHERE item.id = :itemId
            """)
    void deleteById(@Param("itemId") final Long itemId);

    @Modifying
    @Query("""
            UPDATE Item item
            SET item.status = 'DELETED'
            WHERE item.id IN :itemIds
            """)
    void deleteByIds(@Param("itemIds") final List<Long> itemIds);
}
