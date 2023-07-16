import { TripsType } from '@type/trips';
import { Box, Flex, Toggle, ToggleGroup } from 'hang-log-design-system';

import TripsItem from '@components/trips/TripsItem/TripsItem';
import {
  TripsItemGridBoxStyling,
  TripsToggleGroupStyling,
} from '@components/trips/TripsItemList/TripsItemList.style';

interface TripItemListProps {
  data: TripsType[];
  sortBy: string | number;
  changeSelect: (selectedId: string | number) => void;
}

const TripssItemList = ({ data, sortBy, changeSelect }: TripItemListProps) => {
  return (
    data && (
      <>
        <Flex styles={{ justify: 'right', paddingRight: '60px' }} css={TripsToggleGroupStyling}>
          <ToggleGroup>
            <Toggle
              text="등록순"
              toggleId="등록순"
              selectedId={sortBy}
              changeSelect={changeSelect}
            />
            <Toggle
              text="날짜순"
              toggleId="날짜순"
              selectedId={sortBy}
              changeSelect={changeSelect}
            />
          </ToggleGroup>
        </Flex>
        <Box css={TripsItemGridBoxStyling}>
          {data.map((trip) => {
            return (
              <TripsItem
                coverImage={trip.imageUrls}
                badgeName={trip.cities}
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

export default TripssItemList;
