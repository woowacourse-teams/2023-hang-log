import { useGetTrips } from '@/hooks/trips/useGetTrips';

import Header from '@components/layout/Header/Header';
import TripsHeader from '@components/trips/TripsHeader/TripsHeader';
import TripsItemList from '@components/trips/TripsItemList/TripsItemList';

const TripsPage = () => {
  const { data } = useGetTrips();

  return (
    <>
      <Header />
      <TripsHeader />
      <TripsItemList data={data?.data.trips} />
    </>
  );
};

export default TripsPage;
