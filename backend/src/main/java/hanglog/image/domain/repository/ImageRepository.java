package hanglog.image.domain.repository;

import hanglog.image.domain.Image;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Modifying
    @Query("""
                UPDATE Image image
                SET image.status = 'DELETED'
                WHERE image.item.id IN :itemIds
            """)
    void deleteByItemIds(@Param("itemIds") final List<Long> itemIds);
}
