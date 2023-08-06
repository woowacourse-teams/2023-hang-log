import { Flex, Skeleton, Theme } from 'hang-log-design-system';

import ExpenseListSkeleton from '@components/expense/ExpenseList/ExpenseListSkeleton';
import {
  containerStyling,
  toggleGroupStyling,
} from '@components/expense/ExpenseListSection/ExpenseListSection.style';

const ExpenseListSectionSkeleton = () => {
  return (
    <section css={containerStyling}>
      <Skeleton width="125px" height="38px" css={toggleGroupStyling} />
      <Skeleton width="100%" height="48px" />
      <ExpenseListSkeleton />
      <Flex
        styles={{ direction: 'column', gap: Theme.spacer.spacing2 }}
        css={{ marginTop: Theme.spacer.spacing4 }}
      >
        <Skeleton width="220px" height="28px" />
        <Skeleton width="240px" height="16px" />
      </Flex>
    </section>
  );
};

export default ExpenseListSectionSkeleton;
