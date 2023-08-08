import { mediaQueryMobileState, viewportWidthState } from '@store/mediaQuery';
import { Box, Skeleton } from 'hang-log-design-system';
import { useMemo } from 'react';
import { useRecoilValue } from 'recoil';

import {
  contentContainerStyling,
  getContainerStyling,
  informationContainerStyling,
} from '@components/common/TripItem/TripItem.style';

const TripItemSkeleton = () => {
  const isMobile = useRecoilValue(mediaQueryMobileState);
  const viewportWidth = useRecoilValue(viewportWidthState);

  const imageWidth = useMemo(() => viewportWidth - 48, [viewportWidth]);
  const imageHeight = useMemo(() => (imageWidth / 4.5) * 3, [imageWidth]);

  return (
    <li css={getContainerStyling({})}>
      <div css={contentContainerStyling}>
        <Skeleton
          css={{ minWidth: isMobile ? imageWidth : '250px' }}
          width={isMobile ? `${imageWidth}px` : '250px'}
          height={isMobile ? `${imageHeight}px` : '167px'}
        />
        <Box css={informationContainerStyling}>
          <Skeleton width="200px" height="28px" />
          <Skeleton css={{ marginTop: '8px', marginBottom: '16px' }} width="123px" height="20px" />
          <Skeleton css={{ marginBottom: '2px' }} width="90%" height="20px" />
          <Skeleton css={{ marginBottom: '16px' }} width="50%" height="20px" />
          <Skeleton width="100px" height="20px" />
        </Box>
      </div>
    </li>
  );
};

export default TripItemSkeleton;
