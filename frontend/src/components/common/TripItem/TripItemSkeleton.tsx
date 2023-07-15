import { Box, Flex, Skeleton, Theme } from 'hang-log-design-system';

import {
  containerStyling,
  informationContainerStyling,
} from '@components/common/TripItem/TripItem.style';

const TripItemSkeleton = () => {
  return (
    <li css={containerStyling}>
      <Flex styles={{ gap: Theme.spacer.spacing4 }}>
        <Skeleton css={{ minWidth: '250px' }} width="250px" height="167px" />
        <Box css={informationContainerStyling}>
          <Skeleton width="200px" height="28px" />
          <Skeleton css={{ marginTop: '8px', marginBottom: '16px' }} width="123px" height="20px" />
          <Skeleton css={{ marginBottom: '2px' }} width="90%" height="20px" />
          <Skeleton css={{ marginBottom: '16px' }} width="50%" height="20px" />
          <Skeleton width="100px" height="20px" />
        </Box>
      </Flex>
    </li>
  );
};

export default TripItemSkeleton;
