import { EXPENSE_LIST_FILTERS } from '@constants/expense';
import type { ExpenseData } from '@type/expense';
import { Toggle, ToggleGroup, useSelect } from 'hang-log-design-system';

import ExpenseDates from '@components/expense/ExpenseDates/ExpenseDates';
import {
  containerStyling,
  toggleGroupStyling,
} from '@components/expense/ExpenseListSection/ExpenseListSection.style';

import ExpenseCategories from '../ExpenseCategories/ExpenseCategories';

type ExpenseListProps = ExpenseData;

const ExpenseListSection = ({ ...information }: ExpenseListProps) => {
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
        <ExpenseDates tripId={information.id} />
      ) : (
        <ExpenseCategories tripId={information.id} />
      )}
    </section>
  );
};

export default ExpenseListSection;
