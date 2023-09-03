import { useMemo, useState } from 'react';
import { useParams } from 'react-router-dom';

import { Button, Flex, Tab, Tabs, useSelect } from 'hang-log-design-system';

import {
  buttonContainerStyling,
  containerStyling,
  contentStyling,
  mapMobileContainerStyling,
} from '@pages/TripPage/TripPage.style';

import DayLogItem from '@components/common/DayLogItem/DayLogItem';
import GoogleMapWrapper from '@components/common/GoogleMapWrapper/GoogleMapWrapper';
import TripInformation from '@components/common/TripInformation/TripInformation';
import TripMap from '@components/common/TripMap/TripMap';

import { useTripQuery } from '@hooks/api/useTripQuery';
import { useTripDates } from '@hooks/trip/useTripDates';

import { formatMonthDate } from '@utils/formatter';

const TripMobilePage = () => {
  const [isDaylogShown, setIsDaylogShown] = useState(true);
  const { tripId } = useParams();

  const { tripData } = useTripQuery(Number(tripId));

  const { selected: selectedDayLogId, handleSelectClick: handleDayLogIdSelectClick } = useSelect(
    tripData.dayLogs[0].id
  );
  const selectedDayLog = tripData.dayLogs.find((log) => log.id === selectedDayLogId)!;
  const { dates } = useTripDates(Number(tripId));

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
        <section css={contentStyling}>
          <Tabs>
            {dates.map((date, index) => {
              const isLast = index === dates.length - 1;
              const tabText =
                date.id === selectedDayLog.id
                  ? `Day ${index + 1} - ${formatMonthDate(date.date)}  `
                  : `Day ${index + 1}`;

              return (
                <Tab
                  key={date.id}
                  text={isLast ? '기타' : tabText}
                  variant="outline"
                  tabId={date.id}
                  selectedId={selectedDayLog.id}
                  changeSelect={handleDayLogIdSelectClick}
                />
              );
            })}
          </Tabs>
          {isDaylogShown && (
            <DayLogItem tripId={Number(tripId)} isEditable={false} {...selectedDayLog} />
          )}
        </section>
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
        <Button variant="primary" onClick={() => setIsDaylogShown((prev) => !prev)}>
          {isDaylogShown ? '지도 보기' : '데이로그 보기'}
        </Button>
      </div>
    </Flex>
  );
};

export default TripMobilePage;
