package hanglog.trip.domain.category.repository;

import hanglog.trip.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
