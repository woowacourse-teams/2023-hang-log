import { ORDER_BY_DATE, ORDER_BY_REGISTRATION } from '@constants/order';
import { useQueryClient } from '@tanstack/react-query';
import { useSelect } from 'hang-log-design-system';
import { useEffect } from 'react';

import { sortByStartDate } from '@utils/common/sortByStartDate';

import { useGetTrips } from '@hooks/trips/useGetTrips';

import Header from '@components/layout/Header/Header';
import TripsHeader from '@components/trips/TripsHeader/TripsHeader';
import TripsItemList from '@components/trips/TripsItemList/TripsItemList';
import TripsItemNone from '@components/trips/TripsItemNone/TripsItemNone';

const TripsPage = () => {
  const { tripsData } = useGetTrips();
  const { selected: sortSelected, handleSelectClick } = useSelect(ORDER_BY_REGISTRATION);
  const queryClient = useQueryClient();

  useEffect(() => {
    if (sortSelected === ORDER_BY_REGISTRATION) queryClient.invalidateQueries(['trips']);
  }, [sortSelected, queryClient]);

  const sortedTrips =
    sortSelected === ORDER_BY_DATE ? tripsData?.slice().sort(sortByStartDate) : tripsData;

  return (
    <>
      <Header />
      <TripsHeader />
      {sortedTrips?.length ? (
        <TripsItemList trips={sortedTrips} order={sortSelected} changeSelect={handleSelectClick} />
      ) : (
        tripsData && <TripsItemNone />
      )}
    </>
  );
};

export default TripsPage;
