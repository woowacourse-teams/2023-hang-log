import { ORDER_BY_DATE, ORDER_BY_REGISTRATION } from '@constants/order';
import type { TripsData } from '@type/trips';
import { Box, Button, Flex, Heading, Text, Toggle, ToggleGroup } from 'hang-log-design-system';

import { formatDate } from '@utils/formatter';

import TripsItem from '@components/trips/TripsItem/TripsItem';
import {
  emptyBoxStyling,
  gridBoxStyling,
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
    <>
      <Flex styles={{ justify: 'right', paddingRight: '50px' }} css={toggleGroupStyling}>
        <ToggleGroup>
          <Toggle
            text={ORDER_BY_REGISTRATION}
            toggleId={ORDER_BY_REGISTRATION}
            selectedId={order}
            changeSelect={changeSelect}
          />
          <Toggle
            text={ORDER_BY_DATE}
            toggleId={ORDER_BY_DATE}
            selectedId={order}
            changeSelect={changeSelect}
          />
        </ToggleGroup>
      </Flex>
      <Box css={gridBoxStyling}>
        {trips.map((trip) => {
          return (
            <TripsItem
              key={trip.id}
              coverImage={trip.imageUrl}
              cityTags={trip.cities}
              itemName={trip.title}
              duration={`${formatDate(trip.startDate)} - ${formatDate(trip.endDate)}`}
              description={trip.description}
            />
          );
        })}
      </Box>
    </>
  );
};

TripsItemList.Empty = () => {
  return (
    <Box css={emptyBoxStyling}>
      <Heading size="small">아직 기록된 여행이 없습니다!</Heading>
      <Text css={{ padding: '8px 0 16px' }} size="large">
        여행 가방에 쌓인 먼지를 털어내고 여행을 기록해 보세요.
      </Text>
      <Button variant="primary">여행 기록하기</Button>
      <TutorialModal />
    </Box>
  );
};

export default TripsItemList;
