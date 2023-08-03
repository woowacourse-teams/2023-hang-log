import { Flex, FloatingButton, useOverlay, useSelect } from 'hang-log-design-system';
import { useMemo } from 'react';
import { useParams } from 'react-router-dom';

import { useExpenseCategoryQuery } from '@hooks/api/useExpenseCategoryQuery';
import { useTripQuery } from '@hooks/api/useTripQuery';

import {
  addButtonStyling,
  containerStyling,
  mapContainerStyling,
} from '@pages/TripEditPage/TripEditPage.style';

import DayLogList from '@components/common/DayLogList/DayLogList';
import GoogleMapWrapper from '@components/common/GoogleMapWrapper/GoogleMapWrapper';
import TripInformation from '@components/common/TripInformation/TripInformation';
import TripMap from '@components/common/TripMap/TripMap';
import TripItemAddModal from '@components/trip/TripItemAddModal/TripItemAddModal';

const TripEditPage = () => {
  const { tripId } = useParams();

  if (!tripId) throw new Error('존재하지 않는 tripId 입니다.');

  const { tripData } = useTripQuery(Number(tripId));
  useExpenseCategoryQuery();

  const { isOpen: isAddModalOpen, open: openAddModal, close: closeAddModal } = useOverlay();
  const { selected: selectedDayLogId, handleSelectClick: handleDayLogIdSelectClick } = useSelect(
    tripData.dayLogs[0].id
  );
  const selectedDayLog = tripData.dayLogs.find((log) => log.id === selectedDayLogId)!;

  const places = useMemo(
    () =>
      selectedDayLog.items
        .filter((item) => item.itemType)
        .map((item) => ({
          id: item.id,
          name: item.title,
          coordinate: { lat: item.place!.latitude, lng: item.place!.longitude },
        })),
    [selectedDayLog.items]
  );

  return (
    <Flex>
      <section css={containerStyling}>
        <TripInformation {...tripData} />
        <DayLogList
          tripId={Number(tripId)}
          selectedDayLog={selectedDayLog}
          onTabChange={handleDayLogIdSelectClick}
          openAddModal={openAddModal}
        />
        <FloatingButton
          css={addButtonStyling}
          aria-label="여행 아이템 추가"
          onClick={openAddModal}
        />
        {isAddModalOpen && (
          <TripItemAddModal
            tripId={Number(tripId)}
            dayLogId={selectedDayLog.id}
            onClose={closeAddModal}
          />
        )}
      </section>
      <section css={mapContainerStyling}>
        <GoogleMapWrapper>
          <TripMap
            places={places}
            centerLat={tripData.cities[0].latitude}
            centerLng={tripData.cities[0].longitude}
          />
        </GoogleMapWrapper>
      </section>
    </Flex>
  );
};

export default TripEditPage;
