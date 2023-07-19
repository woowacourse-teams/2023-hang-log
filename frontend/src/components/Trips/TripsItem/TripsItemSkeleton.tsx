import { Flex, Skeleton } from 'hang-log-design-system';

import {
  badgeBoxStyling,
  boxStyling,
  imageBoxStyling,
  nameStyling,
  skeletonDurationTextStyling,
} from '@components/trips/TripsItem/TripsItem.style';

const TripsItemSkeleton = () => {
  return (
    <Flex styles={{ direction: 'column' }} css={boxStyling}>
      <Skeleton css={imageBoxStyling} />
      <Skeleton css={badgeBoxStyling} />
      <Skeleton css={nameStyling} height="20px" />
      <Skeleton css={skeletonDurationTextStyling} height="18px" width="60%" />
    </Flex>
  );
};

export default TripsItemSkeleton;