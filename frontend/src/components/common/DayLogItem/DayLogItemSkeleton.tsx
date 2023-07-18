import { Box, Flex, Skeleton } from 'hang-log-design-system';

import { containerStyling, headerStyling } from '@components/common/DayLogItem/DayLogItem.style';
import TripItemListSkeleton from '@components/common/TripItemList/TripItemListSkeleton';

const DayLogItemSkeleton = () => {
  return (
    <Box css={containerStyling}>
      <Flex css={headerStyling} styles={{ justify: 'space-between' }}>
        <Skeleton width="250px" height="38px" />
        <Skeleton width="100px" height="38px" />
      </Flex>
      <TripItemListSkeleton />
    </Box>
  );
};

export default DayLogItemSkeleton;
