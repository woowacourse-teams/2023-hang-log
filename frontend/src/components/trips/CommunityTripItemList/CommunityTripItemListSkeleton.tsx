import { Box } from 'hang-log-design-system';

import TripsItemSkeleton from '@components/trips/TripsItem/TripsItemSkeleton';
import { gridBoxStyling } from '@components/trips/TripsItemList/TripsItemList.style';

interface CommunityTripItemListSkeletonProps {
  length: number;
}

const CommunityTripItemListSkeleton = ({ length }: CommunityTripItemListSkeletonProps) => {
  return (
    <Box tag="ol" css={gridBoxStyling}>
      {Array.from({ length }, (_, index) => {
        return <TripsItemSkeleton key={index} />;
      })}
    </Box>
  );
};

export default CommunityTripItemListSkeleton;
