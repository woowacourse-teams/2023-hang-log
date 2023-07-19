import { FloatingButton } from 'hang-log-design-system';

import { addButtonStyling } from '@pages/TripsPage/TripsPage.style';

import TripsHeader from '@components/trips/TripsHeader/TripsHeader';
import TripsItemListSkeleton from '@components/trips/TripsItemList/TripsItemListSkeleton';

const TripsPageSkeleton = () => {
  return (
    <>
      <TripsHeader />
      <TripsItemListSkeleton />
      <FloatingButton css={addButtonStyling} />
    </>
  );
};

export default TripsPageSkeleton;
