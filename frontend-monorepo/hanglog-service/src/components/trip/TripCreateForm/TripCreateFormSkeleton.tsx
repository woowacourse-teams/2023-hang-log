import { Skeleton } from 'hang-log-design-system';

import { skeletonStyling } from '@components/trip/TripCreateForm/TripCreateForm.style';

const TripCreateFormSkeleton = () => {
  return (
    <>
      <Skeleton css={skeletonStyling} />
      <Skeleton css={skeletonStyling} />
      <Skeleton css={skeletonStyling} />
    </>
  );
};

export default TripCreateFormSkeleton;
