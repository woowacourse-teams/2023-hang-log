import { useSelect } from 'hang-log-design-system';
import { useParams } from 'react-router-dom';

import { useTripQuery } from '@hooks/api/useTripQuery';

import { containerStyling } from '@pages/TripPage/TripPage.style';

import DayLogList from '@components/common/DayLogList/DayLogList';
import TripInformation from '@components/common/TripInformation/TripInformation';

const TripPage = () => {
  const { tripId } = useParams();

  if (!tripId) throw new Error('존재하지 않는 tripId 입니다.');

  const { tripData } = useTripQuery(Number(tripId));

  const { selected: selectedDayLogId, handleSelectClick: handleDayLogIdSelectClick } = useSelect(
    tripData.dayLogs[0].id
  );
  const selectedDayLog = tripData.dayLogs.find((log) => log.id === selectedDayLogId)!;

  return (
    <section css={containerStyling}>
      <TripInformation isEditable={false} {...tripData} />
      <DayLogList
        tripId={Number(tripId)}
        selectedDayLog={selectedDayLog}
        isEditable={false}
        onTabChange={handleDayLogIdSelectClick}
      />
    </section>
  );
};

export default TripPage;
