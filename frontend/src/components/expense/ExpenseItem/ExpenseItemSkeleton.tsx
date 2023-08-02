import { Flex, Skeleton, Theme } from 'hang-log-design-system';

import { containerStyling } from '@components/expense/ExpenseItem/ExpenseItem.style';

const ExpenseItemSkeleton = () => {
  return (
    <Flex tag="li" styles={{ justify: 'space-between', align: 'center' }} css={containerStyling}>
      <Flex styles={{ direction: 'column', gap: Theme.spacer.spacing1 }}>
        <Skeleton width="215px" height="28px" />
        <Skeleton width="100px" height="16px" />
      </Flex>
      <Skeleton width="100px" height="28px" />
    </Flex>
  );
};

export default ExpenseItemSkeleton;
