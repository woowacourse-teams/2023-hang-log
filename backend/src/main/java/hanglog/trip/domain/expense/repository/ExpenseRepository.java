package hanglog.trip.domain.expense.repository;

import hanglog.trip.domain.expense.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
