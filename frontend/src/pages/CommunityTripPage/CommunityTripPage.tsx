import { useMemo } from 'react';
import { useParams } from 'react-router-dom';

import { useRecoilValue } from 'recoil';

import { Flex, useSelect } from 'hang-log-design-system';

import { containerStyling, mapContainerStyling } from '@pages/TripPage/TripPage.style';

import DayLogList from '@components/common/DayLogList/DayLogList';
import GoogleMapWrapper from '@components/common/GoogleMapWrapper/GoogleMapWrapper';
import TripInformation from '@components/common/TripInformation/TripInformation';
import TripMap from '@components/common/TripMap/TripMap';

import { useCommunityTripQuery } from '@hooks/api/useCommunityTripQuery';

import { mediaQueryMobileState } from '@store/mediaQuery';

const CommunityTripPage = () => {
  const { tripId } = useParams();

  if (!tripId) throw new Error('존재하지 않는 공유코드입니다.');

  const { tripData } = useCommunityTripQuery(tripId);

  const isMobile = useRecoilValue(mediaQueryMobileState);

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
        <TripInformation tripId={tripId} isEditable={false} isShared isPublished />
        <DayLogList
          tripId={tripId}
          selectedDayLog={selectedDayLog}
          isEditable={false}
          isShared
          onTabChange={handleDayLogIdSelectClick}
        />
      </section>
      {!isMobile && (
        <section css={mapContainerStyling}>
          <GoogleMapWrapper>
            <TripMap
              places={places}
              centerLat={tripData.cities[0].latitude}
              centerLng={tripData.cities[0].longitude}
            />
          </GoogleMapWrapper>
        </section>
      )}
    </Flex>
  );
};

export default CommunityTripPage;
