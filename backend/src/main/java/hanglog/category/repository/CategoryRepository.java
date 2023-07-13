package hanglog.category.repository;

import hanglog.category.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByGoogleApiId(String googleApiId);
}
