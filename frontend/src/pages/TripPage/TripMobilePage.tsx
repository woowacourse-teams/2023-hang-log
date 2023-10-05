import { TRIP_TYPE } from '@/constants/trip';

import { useState } from 'react';
import { useParams } from 'react-router-dom';

import { Button, Flex, Tab, Tabs } from 'hang-log-design-system';

import {
  buttonContainerStyling,
  buttonStyling,
  containerStyling,
  contentStyling,
  mapMobileContainerStyling,
} from '@pages/TripPage/TripPage.style';

import DayLogItem from '@components/common/DayLogItem/DayLogItem';
import GoogleMapWrapper from '@components/common/GoogleMapWrapper/GoogleMapWrapper';
import TripInformation from '@components/common/TripInformation/TripInformation';
import TripMap from '@components/common/TripMap/TripMap';

import { useTripQuery } from '@hooks/api/useTripQuery';
import { useTripPage } from '@hooks/trip/useTripPage';

import { formatMonthDate } from '@utils/formatter';

import type { TripTypeData } from '@type/trip';

const TripMobilePage = ({ tripType }: { tripType: TripTypeData }) => {
  const [isDaylogShown, setIsDaylogShown] = useState(true);
  const { tripId } = useParams();

  if (!tripId) throw new Error('존재하지 않는 tripId 입니다');

  const { tripData } = useTripQuery(tripType, tripId);
  const { dates, places, selectedDayLog, handleDayLogIdSelectClick } = useTripPage(
    tripType,
    tripId
  );

  return (
    <Flex styles={{ direction: 'column' }}>
      <section css={containerStyling}>
        <TripInformation
          tripType={tripType}
          tripId={tripId}
          isEditable={false}
          isShared={tripType === TRIP_TYPE.SHARED}
          isPublished={tripType === TRIP_TYPE.PUBLISHED}
        />
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
            <DayLogItem
              tripId={tripId}
              isEditable={false}
              isShared={tripType === TRIP_TYPE.SHARED}
              {...selectedDayLog}
            />
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
        <Button
          variant="primary"
          onClick={() => setIsDaylogShown((prev) => !prev)}
          css={buttonStyling}
        >
          {isDaylogShown ? '지도 보기' : '데이로그 보기'}
        </Button>
      </div>
    </Flex>
  );
};

export default TripMobilePage;
