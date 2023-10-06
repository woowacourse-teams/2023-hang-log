import { Flex, Skeleton } from 'hang-log-design-system';

import {
  badgeBoxStyling,
  boxStyling,
  imageStyling,
  nameStyling,
  skeletonDurationTextStyling,
} from '@components/trips/CommunityTripsItem/CommunityTripsItem.style';

const CommunityTripsItemSkeleton = () => {
  return (
    <Flex tag="li" styles={{ direction: 'column' }} css={boxStyling}>
      <Skeleton css={imageStyling} />
      <Skeleton css={badgeBoxStyling} />
      <Skeleton css={nameStyling} height="20px" />
      <Skeleton css={skeletonDurationTextStyling} height="18px" width="60%" />
    </Flex>
  );
};

export default CommunityTripsItemSkeleton;
