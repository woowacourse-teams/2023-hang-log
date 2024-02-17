import { useNavigate } from 'react-router-dom';

import { Box, Button, Flex, Heading, Text, NewToggle as Toggle } from 'hang-log-design-system';

import TripsItem from '@components/trips/TripsItem/TripsItem';
import {
  containerStyling,
  emptyBoxStyling,
  gridBoxStyling,
  headingStyling,
  textStyling,
  toggleGroupStyling,
} from '@components/trips/TripsItemList/TripsItemList.style';
import TutorialModal from '@components/trips/TutorialModal/TutorialModal';

import { formatDate } from '@utils/formatter';

import type { TripsData } from '@type/trips';

import { ORDER_BY_DATE, ORDER_BY_REGISTRATION } from '@constants/order';
import { PATH } from '@constants/path';
import { sortByNewest, sortByStartDate } from '@utils/sort';
import { queryClient } from '@hooks/api/queryClient';

const TripsItemList = () => {
  const tripsData = queryClient.getQueryData(['trips']) as TripsData[];

  const handleSort = (select: number | string) => {
    const sortedTrips =
      select === ORDER_BY_DATE
        ? tripsData?.slice().sort(sortByStartDate)
        : tripsData?.slice().sort(sortByNewest);

    queryClient.setQueryData(['trips'], sortedTrips);
  };

  return (
    <section css={containerStyling}>
      <Toggle initialSelect={ORDER_BY_REGISTRATION} additinalFunc={handleSort}>
        <Flex
          tag="section"
          styles={{ justify: 'right', paddingRight: '50px' }}
          css={toggleGroupStyling}
        >
          <Toggle.List
            text={ORDER_BY_REGISTRATION}
            toggleKey={ORDER_BY_REGISTRATION}
            aria-label="등록순 정렬 버튼"
          />
          <Toggle.List
            text={ORDER_BY_DATE}
            toggleKey={ORDER_BY_DATE}
            aria-label="날짜순 정렬 버튼"
          />
        </Flex>
        <Box tag="ol" css={gridBoxStyling}>
          {tripsData.map((trip, index) => {
            return (
              <TripsItem
                key={trip.id}
                id={trip.id}
                coverImage={trip.imageName}
                cityTags={trip.cities}
                itemName={trip.title}
                duration={`${formatDate(trip.startDate)} - ${formatDate(trip.endDate)}`}
                description={trip.description}
                index={index}
              />
            );
          })}
        </Box>
      </Toggle>
    </section>
  );
};

TripsItemList.Empty = () => {
  // eslint-disable-next-line react-hooks/rules-of-hooks
  const navigate = useNavigate();

  return (
    <Box tag="section" css={[emptyBoxStyling, containerStyling]}>
      <Heading css={headingStyling}>아직 기록된 여행이 없습니다!</Heading>
      <Text css={textStyling}>여행 가방에 쌓인 먼지를 털어내고 여행을 기록해 보세요.</Text>
      <Button variant="primary" onClick={() => navigate(PATH.CREATE_TRIP)}>
        여행 기록하기
      </Button>
      <TutorialModal />
    </Box>
  );
};

export default TripsItemList;
