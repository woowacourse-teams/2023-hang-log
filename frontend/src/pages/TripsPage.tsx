import Header from '@components/layout/Header/Header';
import TripsHeader from '@components/trips/TripsHeader/TripsHeader';
import TripsItemList from '@components/trips/TripsItemList/TripsItemList';

const TripsPage = () => {
  return (
    <>
      <Header />
      <TripsHeader />
      <TripsItemList />
    </>
  );
};

export default TripsPage;
