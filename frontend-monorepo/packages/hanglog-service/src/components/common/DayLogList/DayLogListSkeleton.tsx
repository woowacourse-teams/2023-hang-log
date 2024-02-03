import { Skeleton } from 'hang-log-design-system';

import DayLogItemSkeleton from '@components/common/DayLogItem/DayLogItemSkeleton';
import { containerStyling } from '@components/common/DayLogList/DayLogList.style';

const DayLogListSkeleton = () => {
  return (
    <section css={containerStyling}>
      <Skeleton height="48px" />
      <DayLogItemSkeleton />
    </section>
  );
};

export default DayLogListSkeleton;
