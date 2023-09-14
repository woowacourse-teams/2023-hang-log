import { Box } from 'hang-log-design-system';

import TripsItemSkeleton from '@components/trips/TripsItem/TripsItemSkeleton';
import { gridBoxStyling } from '@components/trips/TripsItemList/TripsItemList.style';

const RecommendedTripsItemListSkeleton = () => {
  return (
    <Box tag="ol" css={gridBoxStyling}>
      {Array.from({ length: 5 }, (_, index) => {
        return <TripsItemSkeleton key={index} />;
      })}
    </Box>
  );
};

export default RecommendedTripsItemListSkeleton;
