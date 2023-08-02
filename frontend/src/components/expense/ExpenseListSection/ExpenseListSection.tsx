import { EXPENSE_LIST_FILTERS } from '@constants/expense';
import { Toggle, ToggleGroup, useSelect } from 'hang-log-design-system';

import ExpenseCategories from '@components/expense/ExpenseCategories/ExpenseCategories';
import ExpenseDates from '@components/expense/ExpenseDates/ExpenseDates';
import {
  containerStyling,
  toggleGroupStyling,
} from '@components/expense/ExpenseListSection/ExpenseListSection.style';

interface ExpenseListProps {
  tripId: number;
}

const ExpenseListSection = ({ tripId }: ExpenseListProps) => {
  const { selected: selectedFilter, handleSelectClick: handleFilterSelectClick } = useSelect(
    EXPENSE_LIST_FILTERS.DAY_LOG
  );

  return (
    <section css={containerStyling}>
      <ToggleGroup css={toggleGroupStyling}>
        <Toggle
          text={EXPENSE_LIST_FILTERS.DAY_LOG}
          toggleId={EXPENSE_LIST_FILTERS.DAY_LOG}
          selectedId={selectedFilter}
          changeSelect={handleFilterSelectClick}
          aria-label="날짜 필터"
        />
        <Toggle
          text={EXPENSE_LIST_FILTERS.CATEGORY}
          toggleId={EXPENSE_LIST_FILTERS.CATEGORY}
          selectedId={selectedFilter}
          changeSelect={handleFilterSelectClick}
          aria-label="카테고리 필터"
        />
      </ToggleGroup>
      {selectedFilter === EXPENSE_LIST_FILTERS.DAY_LOG ? (
        <ExpenseDates tripId={tripId} />
      ) : (
        <ExpenseCategories tripId={tripId} />
      )}
    </section>
  );
};

export default ExpenseListSection;
