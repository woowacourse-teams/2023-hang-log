import { useRecoilValue } from 'recoil';

import { Box, Flex, Skeleton } from 'hang-log-design-system';

import { containerStyling, headerStyling } from '@components/common/DayLogItem/DayLogItem.style';
import TripItemListSkeleton from '@components/common/TripItemList/TripItemListSkeleton';

import { mediaQueryMobileState } from '@store/mediaQuery';

const DayLogItemSkeleton = () => {
  const isMobile = useRecoilValue(mediaQueryMobileState);

  return (
    <Box css={containerStyling}>
      <Flex css={headerStyling} styles={{ justify: 'space-between' }}>
        <Skeleton width={isMobile ? '180px' : '250px'} height="38px" />
      </Flex>
      <TripItemListSkeleton />
    </Box>
  );
};

export default DayLogItemSkeleton;
