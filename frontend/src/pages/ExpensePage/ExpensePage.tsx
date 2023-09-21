import { useParams } from 'react-router-dom';

import { useRecoilValue } from 'recoil';

import { Divider, Flex } from 'hang-log-design-system';

import { containerStyling, dividerStyling } from '@pages/ExpensePage/ExpensePage.style';

import ExpenseListSection from '@components/expense/ExpenseListSection/ExpenseListSection';
import TotalExpenseSection from '@components/expense/TotalExpenseSection/TotalExpenseSection';

import { useExpenseQuery } from '@hooks/api/useExpenseQuery';

import { mediaQueryMobileState } from '@store/mediaQuery';

interface ExpensePageProps {
  isShared?: boolean;
}

const ExpensePage = ({ isShared = false }: ExpensePageProps) => {
  const { tripId } = useParams();

  if (!tripId) throw new Error('존재하지 않는 tripId 입니다.');

  const isMobile = useRecoilValue(mediaQueryMobileState);

  const { expenseData } = useExpenseQuery(tripId, isShared);

  return (
    <Flex css={containerStyling}>
      <TotalExpenseSection tripId={String(expenseData.id)} isShared={isShared} />
      {isMobile && <Divider css={dividerStyling} />}
      <ExpenseListSection tripId={String(expenseData.id)} />
    </Flex>
  );
};

export default ExpensePage;
