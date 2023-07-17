import { ORDER_BY_DATE, ORDER_BY_REGISTRATION } from '@/constants/order';
import { useSelect } from 'hang-log-design-system';
import { useEffect } from 'react';

import { sortByStartDate } from '@utils/common/sortByStartDate';

import { useGetTrips } from '@hooks/trips/useGetTrips';

import Header from '@components/layout/Header/Header';
import TripsHeader from '@components/trips/TripsHeader/TripsHeader';
import TripsItemList from '@components/trips/TripsItemList/TripsItemList';
import TripsItemNone from '@components/trips/TripsItemNone/TripsItemNone';

const TripsPage = () => {
  const { tripsData, refetchTripsData } = useGetTrips();
  const { selected, handleSelectClick } = useSelect(ORDER_BY_REGISTRATION);

  useEffect(() => {
    if (selected === ORDER_BY_REGISTRATION) refetchTripsData();
  }, [selected, refetchTripsData]);

  const sortedTrips =
    selected === ORDER_BY_DATE ? tripsData?.slice().sort(sortByStartDate) : tripsData;

  return (
    <>
      <Header />
      <TripsHeader />
      {tripsData?.length ? (
        <TripsItemList
          trips={tripsData ?? sortedTrips}
          order={selected}
          changeSelect={handleSelectClick}
        />
      ) : (
        tripsData && <TripsItemNone />
      )}
    </>
  );
};

export default TripsPage;
