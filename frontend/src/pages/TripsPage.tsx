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
  const { selected, handleSelectClick } = useSelect('등록순');

  useEffect(() => {
    if (selected === '등록순') refetch();
  }, [selected, refetch]);

  const sortedTrips =
    selected === '날짜순' ? data?.data.trips.slice().sort(sortByStartDate) : data?.data.trips;

  return (
    <>
      <Header />
      <TripsHeader />
      {data?.data.trips.length ? (
        <TripsItemList data={sortedTrips} sortBy={selected} changeSelect={handleSelectClick} />
      ) : (
        data && <TripsItemNone />
      )}
    </>
  );
};

export default TripsPage;
