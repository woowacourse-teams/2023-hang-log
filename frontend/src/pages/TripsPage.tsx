import { useGetTrips } from '@hooks/trips/useGetTrips';

import Header from '@components/layout/Header/Header';
import TripsHeader from '@components/trips/TripsHeader/TripsHeader';
import TripsItemList from '@components/trips/TripsItemList/TripsItemList';
import TripsItemNone from '@components/trips/TripsItemNone/TripsItemNone';

const TripsPage = () => {
  const { data } = useGetTrips();

  return (
    <>
      <Header />
      <TripsHeader />
      {data?.data.trips.length ? <TripsItemList data={data?.data.trips} /> : <TripsItemNone />}
    </>
  );
};

export default TripsPage;
