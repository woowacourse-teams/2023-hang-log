import { Fragment } from 'react';

import { Divider, Flex, Theme } from 'hang-log-design-system';

import ExpenseItemSkeleton from '@components/expense/ExpenseItem/ExpenseItemSkeleton';
import { containerStyling } from '@components/expense/ExpenseList/ExpenseList.style';

import { EXPENSE_LIST_SKELETON_LENGTH } from '@constants/ui';

const ExpenseListSkeleton = () => {
  return (
    <Flex
      tag="ol"
      styles={{ direction: 'column', gap: Theme.spacer.spacing4 }}
      css={containerStyling}
    >
      {Array.from({ length: EXPENSE_LIST_SKELETON_LENGTH }, (_, index) => (
        <Fragment key={index}>
          <ExpenseItemSkeleton />
          <Divider />
        </Fragment>
      ))}
    </Flex>
  );
};

export default ExpenseListSkeleton;
