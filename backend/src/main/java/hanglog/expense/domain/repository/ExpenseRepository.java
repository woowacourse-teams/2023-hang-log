package hanglog.expense.domain.repository;

import hanglog.expense.domain.Expense;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Modifying
    @Query("""
                UPDATE Expense expense
                SET expense.status = 'DELETED'
                WHERE expense.id IN :expenseIds
            """)
    void deleteByIds(@Param("expenseIds") final List<Long> expenseIds);
}
