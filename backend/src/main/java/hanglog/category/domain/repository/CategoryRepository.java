package hanglog.category.domain.repository;

import hanglog.category.domain.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE MOD(c.id, 100) = 0")
    List<Category> findExpenseCategory();
}
