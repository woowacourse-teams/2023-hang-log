import { useNavigate } from 'react-router-dom';

import { FloatingButton, useSelect } from 'hang-log-design-system';

import { addButtonStyling } from '@pages/MyTripsPage/MyTripsPage.style';

import TripsHeader from '@components/trips/TripsHeader/TripsHeader';
import TripsItemList from '@components/trips/TripsItemList/TripsItemList';

import { useTripsQuery } from '@hooks/api/useTripsQuery';

import { sortByNewest, sortByStartDate } from '@utils/sort';

import { ORDER_BY_DATE, ORDER_BY_REGISTRATION } from '@constants/order';
import { PATH } from '@constants/path';

const TripsPage = () => {
  const navigate = useNavigate();
  const { tripsData } = useTripsQuery();

  return (
    <>
      <TripsHeader />
      {tripsData.length > 0 ? <TripsItemList /> : <TripsItemList.Empty />}
      <FloatingButton css={addButtonStyling} onClick={() => navigate(PATH.CREATE_TRIP)} />
    </>
  );
};

export default TripsPage;
