import { useParams } from 'react-router-dom';

import { Flex } from 'hang-log-design-system';

import { containerStyling, mapContainerStyling } from '@pages/TripPage/TripPage.style';

import DayLogList from '@components/common/DayLogList/DayLogList';
import GoogleMapWrapper from '@components/common/GoogleMapWrapper/GoogleMapWrapper';
import TripInformation from '@components/common/TripInformation/TripInformation';
import TripMap from '@components/common/TripMap/TripMap';

import { useTripQuery } from '@hooks/api/useTripQuery';
import { useTripPage } from '@hooks/trip/useTripPage';

const TripPage = () => {
  const { tripId } = useParams();

  if (!tripId) throw new Error('존재하지 않는 tripId 입니다.');

  const { tripData } = useTripQuery('PERSONAL', tripId);

  const { places, selectedDayLog, handleDayLogIdSelectClick } = useTripPage(
    tripData.tripType,
    tripId
  );

  return (
    <Flex>
      <section css={containerStyling}>
        <TripInformation tripType={tripData.tripType} tripId={tripId} isEditable={false} />
        <DayLogList
          tripType={tripData.tripType}
          tripId={tripId}
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
