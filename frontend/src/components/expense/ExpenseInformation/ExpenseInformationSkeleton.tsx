import { Flex, Skeleton, Theme } from 'hang-log-design-system';

import { sectionStyling } from '@components/expense/ExpenseInformation/ExpenseInformation.style';

const ExpenseInformationSkeleton = () => {
  return (
    <header css={sectionStyling}>
      <Flex styles={{ gap: Theme.spacer.spacing1 }}>
        <Skeleton width="150px" height="28px" />
      </Flex>
      <Skeleton width="220px" height="40px" css={{ marginTop: Theme.spacer.spacing3 }} />
      <Skeleton width="180px" height="16px" css={{ marginTop: Theme.spacer.spacing3 }} />
    </header>
  );
};

export default ExpenseInformationSkeleton;
