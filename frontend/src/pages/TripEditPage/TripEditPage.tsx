import { trip } from '@mocks/data/trip';
import { FloatingButton } from 'hang-log-design-system';

import DayLogList from '@components/common/DayLogList/DayLogList';
import TripInformation from '@components/common/TripInformation/TripInformation';

import { addButtonStyling, containerStyling } from './TripEditPage.style';

const TripEditPage = () => {
  return (
    <>
      <section css={containerStyling}>
        <TripInformation {...trip} />
        <DayLogList logs={trip.dayLogs} />
        <FloatingButton css={addButtonStyling} />
      </section>
    </>
  );
};

export default TripEditPage;
