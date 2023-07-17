import TripsItem from '@/components/trips/TripsItem/TripsItem';
import {
  TripsItemGridBoxStyling,
  TripsToggleGroupStyling,
} from '@/components/trips/TripsItemList/TripsItemList.style';
import { ORDER_BY_DATE, ORDER_BY_REGISTRATION } from '@constants/order';
import { TripsType } from '@type/trips';
import { Box, Flex, Toggle, ToggleGroup } from 'hang-log-design-system';

interface TripsItemListProps {
  trips: TripsType[];
  order: string | number;
  changeSelect: (selectedId: string | number) => void;
}

const TripsItemList = ({ trips, order, changeSelect }: TripsItemListProps) => {
  return (
    trips && (
      <>
        <Flex styles={{ justify: 'right', paddingRight: '50px' }} css={TripsToggleGroupStyling}>
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
        <Box css={TripsItemGridBoxStyling}>
          {trips.map((trip) => {
            return (
              <TripsItem
                key={trip.id}
                coverImage={trip.imageUrls}
                cityTags={trip.cities}
                itemName={trip.title}
                duration={`${trip.startDate}~${trip.endDate}`}
                description={trip.description}
              />
            );
          })}
        </Box>
      </>
    )
  );
};

export default TripsItemList;
