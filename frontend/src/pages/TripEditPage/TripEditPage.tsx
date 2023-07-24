import { FloatingButton, useOverlay, useSelect } from 'hang-log-design-system';
import { useParams } from 'react-router-dom';

import { useExpenseCategoryQuery } from '@hooks/api/useExpenseCategoryQuery';
import { useTripQuery } from '@hooks/api/useTripQuery';
import { useTripDates } from '@hooks/trip/useTripDates';

import { addButtonStyling, containerStyling } from '@pages/TripEditPage/TripEditPage.style';

import DayLogList from '@components/common/DayLogList/DayLogList';
import TripInformation from '@components/common/TripInformation/TripInformation';
import TripItemAddModal from '@components/trip/TripItemAddModal/TripItemAddModal';

const TripEditPage = () => {
  const { tripId } = useParams();

  if (!tripId) throw new Error(`tripId doesn't exist`);

  const { tripData } = useTripQuery(Number(tripId));
  useExpenseCategoryQuery();

  const { isOpen: isAddModalOpen, open: openAddModal, close: closeAddModal } = useOverlay();
  const { selected: selectedDayLogId, handleSelectClick: handleDayLogIdSelectClick } = useSelect(
    tripData.dayLogs[0].id
  );
  const { tripDates } = useTripDates(Number(tripId));
  const selectedDayLog = tripData.dayLogs.find((log) => log.id === selectedDayLogId)!;

  return (
    <section css={containerStyling}>
      <TripInformation {...tripData} />
      <DayLogList
        tripId={Number(tripId)}
        selectedDayLog={selectedDayLog}
        dates={tripDates}
        onTabChange={handleDayLogIdSelectClick}
        openAddModal={openAddModal}
      />
      <FloatingButton css={addButtonStyling} onClick={openAddModal} />
      {isAddModalOpen && (
        <TripItemAddModal
          tripId={Number(tripId)}
          dayLogId={selectedDayLog.id}
          dates={tripDates}
          onClose={closeAddModal}
        />
      )}
    </section>
  );
};

export default TripEditPage;
