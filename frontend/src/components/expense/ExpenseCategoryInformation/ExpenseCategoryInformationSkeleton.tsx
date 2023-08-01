import { EXPENSE_CATEGORY_INFORMATION_SKELETON_LENGTH } from '@constants/ui';
import { Skeleton } from 'hang-log-design-system';

import { containerStyling } from '@components/expense/ExpenseCategoryInformation/ExpenseCategoryInformation.style';

const ExpenseCategoryInformationSkeleton = () => {
  return (
    <div css={containerStyling}>
      {Array.from({ length: EXPENSE_CATEGORY_INFORMATION_SKELETON_LENGTH }, (_, index) => (
        <Skeleton key={index} width="100%" height="24px" />
      ))}
    </div>
  );
};

export default ExpenseCategoryInformationSkeleton;
