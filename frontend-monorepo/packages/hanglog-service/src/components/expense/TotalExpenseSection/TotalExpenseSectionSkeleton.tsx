import { Box, Skeleton } from 'hang-log-design-system';

import ExpenseCategoryInformationSkeleton from '@components/expense/ExpenseCategoryInformation/ExpenseCategoryInformationSkeleton';
import ExpenseInformationSkeleton from '@components/expense/ExpenseInformation/ExpenseInformationSkeleton';
import {
  containerStyling,
  totalAmountStyling,
} from '@components/expense/TotalExpenseSection/TotalExpenseSection.style';

const TotalExpenseSectionSkeleton = () => {
  return (
    <section css={containerStyling}>
      <ExpenseInformationSkeleton />
      <Skeleton width="200px" height="32px" css={totalAmountStyling} />
      <Box
        css={{
          position: 'relative',
          width: '300px',
        }}
      >
        <Skeleton width="300px" height="300px" variant="circle" />
      </Box>
      <ExpenseCategoryInformationSkeleton />
    </section>
  );
};

export default TotalExpenseSectionSkeleton;
