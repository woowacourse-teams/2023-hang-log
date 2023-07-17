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
  const { data, refetch } = useGetTrips();
  const { selected, handleSelectClick } = useSelect(ORDER_BY_REGISTRATION);

  useEffect(() => {
    if (selected === ORDER_BY_REGISTRATION) refetch();
  }, [selected, refetch]);

  const sortedTrips =
    selected === ORDER_BY_DATE ? data?.data.trips.slice().sort(sortByStartDate) : data?.data.trips;

  return (
    <>
      <Header />
      <TripsHeader />
      {data?.data.trips.length ? (
        <TripsItemList trips={sortedTrips} order={selected} changeSelect={handleSelectClick} />
      ) : (
        data && <TripsItemNone />
      )}
    </>
  );
};

export default TripsPage;
