import { useMemo, useState } from 'react';
import { useParams } from 'react-router-dom';

import { Button, Flex, useSelect } from 'hang-log-design-system';

import {
  buttonContainerStyling,
  containerStyling,
  mapMobileContainerStyling,
  mobileSwitchButtonStyling,
} from '@pages/TripPage/TripPage.style';

import DayLogList from '@components/common/DayLogList/DayLogList';
import GoogleMapWrapper from '@components/common/GoogleMapWrapper/GoogleMapWrapper';
import TripInformation from '@components/common/TripInformation/TripInformation';
import TripMap from '@components/common/TripMap/TripMap';

import { useTripQuery } from '@hooks/api/useTripQuery';

const TripMobilePage = () => {
  const [isDaylogShown, setIsDaylogShown] = useState(true);
  const { tripId } = useParams();

  const { tripData } = useTripQuery(Number(tripId));

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
    <Flex styles={{ direction: 'column' }}>
      <section css={containerStyling}>
        <TripInformation isEditable={false} {...tripData} />
        {isDaylogShown && (
          <DayLogList
            tripId={Number(tripId)}
            selectedDayLog={selectedDayLog}
            isEditable={false}
            onTabChange={handleDayLogIdSelectClick}
          />
        )}
      </section>
      {!isDaylogShown && (
        <section css={mapMobileContainerStyling}>
          <GoogleMapWrapper>
            <TripMap
              places={places}
              centerLat={tripData.cities[0].latitude}
              centerLng={tripData.cities[0].longitude}
            />
          </GoogleMapWrapper>
        </section>
      )}
      <div css={buttonContainerStyling}>
        <Button
          variant="primary"
          onClick={() => setIsDaylogShown((prev) => !prev)}
          css={mobileSwitchButtonStyling}
        >
          {isDaylogShown ? '지도 보기' : '데이로그 보기'}
        </Button>
      </div>
    </Flex>
  );
};

export default TripMobilePage;
