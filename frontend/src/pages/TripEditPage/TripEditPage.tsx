import { FloatingButton } from 'hang-log-design-system';
import { useParams } from 'react-router-dom';

import { useTripQuery } from '@hooks/api/useTripQuery';

import { addButtonStyling, containerStyling } from '@pages/TripEditPage/TripEditPage.style';

import DayLogList from '@components/common/DayLogList/DayLogList';
import TripInformation from '@components/common/TripInformation/TripInformation';

const TripEditPage = () => {
  const { tripId } = useParams();

  if (!tripId) throw new Error(`tripId doesn't exist`);

  const { tripData } = useTripQuery(Number(tripId));

  return (
    <section css={containerStyling}>
      <TripInformation {...tripData} />
      <DayLogList tripId={Number(tripId)} logs={tripData.dayLogs} />
      <FloatingButton css={addButtonStyling} />
    </section>
  );
};

export default TripEditPage;
