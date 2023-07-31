import DefaultThumbnail from '@assets/png/trip_default-thumbnail.png';
import type { CityData } from '@type/city';
import { Badge, Box, Flex, Text } from 'hang-log-design-system';

import {
  badgeBoxStyling,
  boxStyling,
  durationTextStyling,
  imageStyling,
  nameStyling,
} from '@components/trips/TripsItem/TripsItem.style';

interface TripsItemProps {
  coverImage: string | null;
  cityTags: CityData[];
  itemName: string;
  duration: string;
  index: number;
  description?: string | null;
}

const TripsItem = ({
  coverImage,
  cityTags,
  itemName,
  duration,
  description,
  index,
}: TripsItemProps) => {
  return (
    <Flex
      tag="li"
      styles={{ direction: 'column' }}
      css={boxStyling}
      tabIndex={index + 5}
      aria-label={index + 1 + '번째 trip,' + itemName}
    >
      <img
        src={coverImage ?? DefaultThumbnail}
        css={imageStyling}
        alt={`${itemName} 대표 이미지`}
      />
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
