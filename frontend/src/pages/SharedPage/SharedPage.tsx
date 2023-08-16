import { useEffect, useMemo } from 'react';
import { useParams } from 'react-router-dom';

import { useRecoilValue, useSetRecoilState } from 'recoil';

import { Flex, useSelect } from 'hang-log-design-system';

import { containerStyling, mapContainerStyling } from '@pages/TripPage/TripPage.style';

import DayLogList from '@components/common/DayLogList/DayLogList';
import GoogleMapWrapper from '@components/common/GoogleMapWrapper/GoogleMapWrapper';
import TripInformation from '@components/common/TripInformation/TripInformation';
import TripMap from '@components/common/TripMap/TripMap';

import { useSharedQuery } from '@hooks/api/useSharedTripQuery';

import { isLoggedInState } from '@store/auth';
import { mediaQueryMobileState } from '@store/mediaQuery';

const SharedPage = () => {
  const { code } = useParams();
  const setIsLoggedIn = useSetRecoilState(isLoggedInState);

  if (!code) throw new Error('존재하지 않는 공유코드입니다.');

  const { tripData } = useSharedQuery(code);

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

  useEffect(() => {
    setIsLoggedIn(false);
  }, [setIsLoggedIn]);

  return (
    <Flex>
      <section css={containerStyling}>
        <TripInformation isEditable={false} {...tripData} />
        <DayLogList
          tripId={Number(code)}
          selectedDayLog={selectedDayLog}
          isEditable={false}
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

export default SharedPage;
