import { Suspense } from 'react';

import { Box, Skeleton, Text } from 'hang-log-design-system';

import {
  pagenationSkeletonStyling,
  subTitleStyling,
} from '@pages/CommunityPage/CommunityPage.style';

import CommunityHeader from '@components/community/CommunityHeader/CommunityHeader';
import CommunityTripItemListSkeleton from '@components/trips/CommunityTripItemList/CommunityTripItemListSkeleton';
import TripsHeaderSkeleton from '@components/trips/TripsHeader/TripsHeaderSkeleton';

const CommunityPageSkeleton = () => {
  return (
    <>
      <Suspense fallback={<TripsHeaderSkeleton />}>
        <CommunityHeader />
      </Suspense>
      <Box css={subTitleStyling(57, 32)}>
        <Skeleton width="200px" height="24px" />
      </Box>
      <CommunityTripItemListSkeleton length={5} />
      <Text css={subTitleStyling(57, 32)}>더 많은 여행들을 확인해보세요</Text>
      <CommunityTripItemListSkeleton length={10} />
      <div css={pagenationSkeletonStyling}>
        <Skeleton />
      </div>
    </>
  );
};

export default CommunityPageSkeleton;
