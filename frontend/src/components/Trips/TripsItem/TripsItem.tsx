import { Cities } from '@type/trips';
import { Badge, Box, Flex, Text } from 'hang-log-design-system';

import {
  tripsImageBoxStyling,
  tripsItemBadgeBoxStyling,
  tripsItemImageBoxStyling,
  tripsItemImageStyling,
  tripsItemNameStying,
} from '@components/trips/TripsItem/TripsItem.style';

interface TripItemProps {
  coverImage: string;
  badgeName: Cities[];
  itemName: string;
  duration: string;
  description?: string;
}

const TripsItem = ({ coverImage, badgeName, itemName, duration, description }: TripItemProps) => {
  return (
    <Flex styles={{ direction: 'column' }} css={tripsImageBoxStyling}>
      <Box css={tripsItemImageBoxStyling}>
        <img src={coverImage} css={tripsItemImageStyling} />
      </Box>
      <Box css={tripsItemBadgeBoxStyling}>
        {badgeName.map((data) => {
          return <Badge key={data.id}>{data.name}</Badge>;
        })}
      </Box>
      <Text size="large" css={tripsItemNameStying}>
        {itemName}
      </Text>
      <Text>{duration}</Text>
      <Text>{description}</Text>
    </Flex>
  );
};

export default TripsItem;
