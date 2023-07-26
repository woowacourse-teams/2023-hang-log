import { ORDER_BY_DATE, ORDER_BY_REGISTRATION } from '@constants/order';
import { PATH } from '@constants/path';
import { FloatingButton, useSelect } from 'hang-log-design-system';
import { useNavigate } from 'react-router-dom';

import { sortByStartDate } from '@utils/sortByStartDate';

import { useTripsQuery } from '@hooks/api/useTripsQuery';

import { addButtonStyling } from '@pages/TripsPage/TripsPage.style';

import TripsHeader from '@components/trips/TripsHeader/TripsHeader';
import TripsItemList from '@components/trips/TripsItemList/TripsItemList';

const TripsPage = () => {
  const navigate = useNavigate();
  const { tripsData } = useTripsQuery();
  const { selected: sortSelected, handleSelectClick: handleSortSelectClick } =
    useSelect(ORDER_BY_REGISTRATION);

  const sortedTrips =
    sortSelected === ORDER_BY_DATE ? tripsData?.slice().sort(sortByStartDate) : tripsData;

  return (
    <>
      <TripsHeader />
      {sortedTrips?.length ? (
        <TripsItemList
          trips={sortedTrips}
          order={sortSelected}
          changeSelect={handleSortSelectClick}
        />
      ) : (
        <TripsItemList.Empty />
      )}
      <FloatingButton css={addButtonStyling} onClick={() => navigate(PATH.CREATE_TRIP)} />
    </>
  );
};

export default TripsPage;
