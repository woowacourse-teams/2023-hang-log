import { ORDER_BY_DATE, ORDER_BY_REGISTRATION } from '@constants/order';
import { PATH } from '@constants/path';
import type { TripsData } from '@type/trips';
import { Box, Button, Flex, Heading, Text, Toggle, ToggleGroup } from 'hang-log-design-system';
import { useNavigate } from 'react-router-dom';

import { formatDate } from '@utils/formatter';

import TripsItem from '@components/trips/TripsItem/TripsItem';
import {
  containerStyling,
  emptyBoxStyling,
  gridBoxStyling,
  textStyling,
  toggleGroupStyling,
} from '@components/trips/TripsItemList/TripsItemList.style';
import TutorialModal from '@components/trips/TutorialModal/TutorialModal';

interface TripsItemListProps {
  trips: TripsData[];
  order: string | number;
  changeSelect: (selectedId: string | number) => void;
}

const TripsItemList = ({ trips, order, changeSelect }: TripsItemListProps) => {
  return (
    <section css={containerStyling}>
      <Flex
        tag="section"
        styles={{ justify: 'right', paddingRight: '50px' }}
        css={toggleGroupStyling}
      >
        <ToggleGroup>
          <Toggle
            text={ORDER_BY_REGISTRATION}
            toggleId={ORDER_BY_REGISTRATION}
            selectedId={order}
            changeSelect={changeSelect}
            aria-label="등록순 정렬 버튼"
          />
          <Toggle
            text={ORDER_BY_DATE}
            toggleId={ORDER_BY_DATE}
            selectedId={order}
            changeSelect={changeSelect}
            aria-label="날짜순 정렬 버튼"
          />
        </ToggleGroup>
      </Flex>
      <Box tag="ol" css={gridBoxStyling}>
        {trips.map((trip, index) => {
          return (
            <TripsItem
              key={trip.id}
              id={trip.id}
              coverImage={trip.imageUrl}
              cityTags={trip.cities}
              itemName={trip.title}
              duration={`${formatDate(trip.startDate)} - ${formatDate(trip.endDate)}`}
              description={trip.description}
              index={index}
            />
          );
        })}
      </Box>
    </section>
  );
};

TripsItemList.Empty = () => {
  // eslint-disable-next-line react-hooks/rules-of-hooks
  const navigate = useNavigate();

  return (
    <Box tag="section" css={[emptyBoxStyling, containerStyling]}>
      <Heading size="small">아직 기록된 여행이 없습니다!</Heading>
      <Text size="large" css={textStyling}>
        여행 가방에 쌓인 먼지를 털어내고 여행을 기록해 보세요.
      </Text>
      <Button variant="primary" onClick={() => navigate(PATH.CREATE_TRIP)}>
        여행 기록하기
      </Button>
      <TutorialModal />
    </Box>
  );
};

export default TripsItemList;
