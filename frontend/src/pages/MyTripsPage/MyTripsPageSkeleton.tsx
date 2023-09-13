import { addButtonStyling } from '@/pages/MyTripsPage/MyTripsPage.style';

import { Suspense } from 'react';

import { FloatingButton } from 'hang-log-design-system';

import TripsHeader from '@components/trips/TripsHeader/TripsHeader';
import TripsHeaderSkeleton from '@components/trips/TripsHeader/TripsHeaderSkeleton';
import TripsItemListSkeleton from '@components/trips/TripsItemList/TripsItemListSkeleton';

const TripsPageSkeleton = () => {
  return (
    <>
      <Suspense fallback={<TripsHeaderSkeleton />}>
        <TripsHeader />
      </Suspense>
      <TripsItemListSkeleton />
      <FloatingButton css={addButtonStyling} />
    </>
  );
};

export default TripsPageSkeleton;
