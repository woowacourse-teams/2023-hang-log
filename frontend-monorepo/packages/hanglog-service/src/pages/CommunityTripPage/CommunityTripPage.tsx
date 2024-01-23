import { useParams } from 'react-router-dom';

import { Flex } from 'hang-log-design-system';

import { containerStyling, mapContainerStyling } from '@pages/TripPage/TripPage.style';

import DayLogList from '@components/common/DayLogList/DayLogList';
import GoogleMapWrapper from '@components/common/GoogleMapWrapper/GoogleMapWrapper';
import TripInformation from '@components/common/TripInformation/TripInformation';
import TripMap from '@components/common/TripMap/TripMap';

import { useCommunityTripQuery } from '@hooks/api/useCommunityTripQuery';
import { useTripPage } from '@hooks/trip/useTripPage';

const CommunityTripPage = () => {
  const { tripId } = useParams();

  if (!tripId) throw new Error('존재하지 않는 여행입니다.');

  const { communityTripData } = useCommunityTripQuery(tripId);

  const { places, selectedDayLog, handleDayLogIdSelectClick } = useTripPage(
    communityTripData.tripType,
    tripId
  );

  return (
    <Flex>
      <section css={containerStyling}>
        <TripInformation tripType={communityTripData.tripType} tripId={tripId} isEditable={false} />
        <DayLogList
          tripType={communityTripData.tripType}
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
            centerLat={communityTripData.cities[0].latitude}
            centerLng={communityTripData.cities[0].longitude}
          />
        </GoogleMapWrapper>
      </section>
    </Flex>
  );
};

export default CommunityTripPage;
