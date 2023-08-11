import { useRecoilValue } from 'recoil';

import { Divider, Flex } from 'hang-log-design-system';

import { containerStyling, dividerStyling } from '@pages/ExpensePage/ExpensePage.style';

import ExpenseListSectionSkeleton from '@components/expense/ExpenseListSection/ExpenseListSectionSkeleton';
import TotalExpenseSectionSkeleton from '@components/expense/TotalExpenseSection/TotalExpenseSectionSkeleton';

import { mediaQueryMobileState } from '@store/mediaQuery';

const ExpensePageSkeleton = () => {
  const isMobile = useRecoilValue(mediaQueryMobileState);

  return (
    <Flex css={containerStyling}>
      <TotalExpenseSectionSkeleton />
      {isMobile && <Divider css={dividerStyling} />}
      <ExpenseListSectionSkeleton />
    </Flex>
  );
};

export default ExpensePageSkeleton;
