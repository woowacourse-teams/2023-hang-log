import { Flex } from 'hang-log-design-system';

import { containerStyling } from '@pages/ExpensePage/ExpensePage.style';

import ExpenseListSectionSkeleton from '@components/expense/ExpenseListSection/ExpenseListSectionSkeleton';
import TotalExpenseSectionSkeleton from '@components/expense/TotalExpenseSection/TotalExpenseSectionSkeleton';

const ExpensePageSkeleton = () => {
  return (
    <Flex css={containerStyling}>
      <TotalExpenseSectionSkeleton />
      <ExpenseListSectionSkeleton />
      {/* <ExpenseListSection tripId={expenseData.id} /> */}
    </Flex>
  );
};

export default ExpensePageSkeleton;
