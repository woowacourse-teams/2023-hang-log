import { Flex, Skeleton, Theme } from 'hang-log-design-system';

import { headingStyling, imageStyling } from '@components/trips/TripsHeader/TripsHeader.style';

import TripsHeaderImage from '@assets/svg/TripsHeader.svg';

const TripsHeaderSkeleton = () => {
  return (
    <Flex
      tag="section"
      styles={{
        justify: 'space-between',
        align: 'center',
        width: '100%',
        marginTop: Theme.spacer.spacing3,
        marginBottom: Theme.spacer.spacing4,
      }}
    >
      <Skeleton width="250px" height="42px" css={headingStyling} />
      <TripsHeaderImage css={imageStyling} />
    </Flex>
  );
};

export default TripsHeaderSkeleton;
