import DefaultThumbnail from '@assets/png/trip_default-thumbnail.png';
import type { CityData } from '@type/city';
import { Badge, Box, Flex, Text } from 'hang-log-design-system';

import {
  badgeBoxStyling,
  boxStyling,
  durationTextStyling,
  imageBoxStyling,
  imageStyling,
  nameStyling,
} from '@components/trips/TripsItem/TripsItem.style';

interface TripsItemProps {
  coverImage: string | null;
  cityTags: CityData[];
  itemName: string;
  duration: string;
  description?: string | null;
}

const TripsItem = ({ coverImage, cityTags, itemName, duration, description }: TripsItemProps) => {
  return (
    <Flex tag="li" styles={{ direction: 'column' }} css={boxStyling}>
      <img src={coverImage ?? DefaultThumbnail} css={imageStyling} />
      <Box css={badgeBoxStyling}>
        {cityTags.map((cityTag) => {
          return <Badge key={cityTag.id}>{cityTag.name}</Badge>;
        })}
      </Box>
      <Text size="large" css={nameStyling}>
        {itemName}
      </Text>
      <Text size="medium">{duration}</Text>
      <Text size="small" css={durationTextStyling}>
        {description}
      </Text>
    </Flex>
  );
};

export default TripsItem;
