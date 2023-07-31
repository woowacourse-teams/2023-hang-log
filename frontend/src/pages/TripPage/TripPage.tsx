import { Flex, useSelect } from 'hang-log-design-system';
import { useParams } from 'react-router-dom';

import { useTripQuery } from '@hooks/api/useTripQuery';

import { containerStyling, mapContainerStyling } from '@pages/TripPage/TripPage.style';

import DayLogList from '@components/common/DayLogList/DayLogList';
import GoogleMapWrapper from '@components/common/GoogleMapWrapper/GoogleMapWrapper';
import TripInformation from '@components/common/TripInformation/TripInformation';
import TripMap from '@components/common/TripMap/TripMap';

const TripPage = () => {
  const { tripId } = useParams();

  if (!tripId) throw new Error('존재하지 않는 tripId 입니다.');

  const { tripData } = useTripQuery(Number(tripId));

  const { selected: selectedDayLogId, handleSelectClick: handleDayLogIdSelectClick } = useSelect(
    tripData.dayLogs[0].id
  );
  const selectedDayLog = tripData.dayLogs.find((log) => log.id === selectedDayLogId)!;

  const places = selectedDayLog.items
    .filter((item) => item.itemType)
    .map((item) => ({
      id: item.id,
      coordinate: { lat: item.place!.latitude, lng: item.place!.longitude },
    }));

  return (
    <Flex>
      <section css={containerStyling}>
        <TripInformation isEditable={false} {...tripData} />
        <DayLogList
          tripId={Number(tripId)}
          selectedDayLog={selectedDayLog}
          isEditable={false}
          onTabChange={handleDayLogIdSelectClick}
        />
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

export default TripPage;
