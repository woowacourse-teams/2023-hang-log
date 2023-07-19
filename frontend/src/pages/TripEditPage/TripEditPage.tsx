import { FloatingButton, useOverlay, useSelect } from 'hang-log-design-system';
import { useParams } from 'react-router-dom';

import { useExpenseCategoryQuery } from '@hooks/api/useExpenseCategoryQuery';
import { useTripQuery } from '@hooks/api/useTripQuery';

import { addButtonStyling, containerStyling } from '@pages/TripEditPage/TripEditPage.style';

import DayLogList from '@components/common/DayLogList/DayLogList';
import TripInformation from '@components/common/TripInformation/TripInformation';
import TripItemAddModal from '@components/trip/TripItemAddModal/TripItemAddModal';

const TripEditPage = () => {
  const { tripId } = useParams();

  if (!tripId) throw new Error(`tripId doesn't exist`);

  const { tripData } = useTripQuery(Number(tripId));
  useExpenseCategoryQuery();

  const { isOpen, open, close } = useOverlay();
  const { selected, handleSelectClick } = useSelect(tripData.dayLogs[0].id);
  const selectedDayLog = tripData.dayLogs.find((log) => log.id === selected)!;

  const dates = tripData.dayLogs.map((data) => ({
    id: data.id,
    date: data.date,
  }));

  return (
    <section css={containerStyling}>
      <TripInformation {...tripData} />
      <DayLogList
        tripId={Number(tripId)}
        selectedDayLog={selectedDayLog}
        dates={dates}
        onTabChange={handleSelectClick}
      />
      <FloatingButton css={addButtonStyling} onClick={open} />
      <TripItemAddModal
        tripId={Number(tripId)}
        isOpen={isOpen}
        dates={dates}
        currentDate={{ id: selectedDayLog.id, date: selectedDayLog.date }}
        onClose={close}
      />
    </section>
  );
};

export default TripEditPage;
