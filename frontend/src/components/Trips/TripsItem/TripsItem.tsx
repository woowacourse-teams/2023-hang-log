import {
  tripsBoxStyling,
  tripsDurationTextStyling,
  tripsItemBadgeBoxStyling,
  tripsItemImageBoxStyling,
  tripsItemImageStyling,
  tripsItemNameStying,
} from '@/components/trips/TripsItem/TripsItem.style';
import { Cities } from '@type/trips';
import { Badge, Box, Flex, Text } from 'hang-log-design-system';

interface TripItemProps {
  coverImage: string;
  cityTags: Cities[];
  itemName: string;
  duration: string;
  description?: string;
}

const TripsItem = ({ coverImage, cityTags, itemName, duration, description }: TripItemProps) => {
  return (
    <Flex styles={{ direction: 'column' }} css={tripsBoxStyling}>
      <Box css={tripsItemImageBoxStyling}>
        <img src={coverImage} css={tripsItemImageStyling} />
      </Box>
      <Box css={tripsItemBadgeBoxStyling}>
        {cityTags.map((data) => {
          return <Badge key={data.id}>{data.name}</Badge>;
        })}
      </Box>
      <Text size="large" css={tripsItemNameStying}>
        {itemName}
      </Text>
      <Text size="medium">{duration}</Text>
      <Text size="small" css={tripsDurationTextStyling}>
        {description}
      </Text>
    </Flex>
  );
};

export default TripsItem;
