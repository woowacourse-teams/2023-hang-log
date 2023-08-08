import { Flex } from 'hang-log-design-system';
import { useParams } from 'react-router-dom';

import { useExpenseQuery } from '@hooks/api/useExpenseQuery';

import { containerStyling } from '@pages/ExpensePage/ExpensePage.style';

import ExpenseListSection from '@components/expense/ExpenseListSection/ExpenseListSection';
import TotalExpenseSection from '@components/expense/TotalExpenseSection/TotalExpenseSection';

const ExpensePage = () => {
  const { tripId } = useParams();

  if (!tripId) throw new Error('존재하지 않는 tripId 입니다.');

  const { expenseData } = useExpenseQuery(Number(tripId));

  return (
    <Flex css={containerStyling}>
      <TotalExpenseSection tripId={expenseData.id} />
      <ExpenseListSection tripId={expenseData.id} />
    </Flex>
  );
};

export default ExpensePage;
