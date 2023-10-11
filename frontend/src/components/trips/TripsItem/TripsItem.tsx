import convertImageName from '@/utils/convertImageName';

import { useNavigate } from 'react-router-dom';

import { Badge, Box, Flex, Text } from 'hang-log-design-system';

import {
  badgeBoxStyling,
  boxStyling,
  descriptionStyling,
  imageStyling,
  nameStyling,
} from '@components/trips/TripsItem/TripsItem.style';

import type { CityData } from '@type/city';

import { PATH } from '@constants/path';

import DefaultThumbnail from '@assets/png/trip_default-thumbnail.png';

interface TripsItemProps {
  id: number;
  coverImage: string | null;
  cityTags: CityData[];
  itemName: string;
  duration: string;
  index: number;
  description?: string | null;
}

const TripsItem = ({
  id,
  coverImage,
  cityTags,
  itemName,
  duration,
  description,
  index,
}: TripsItemProps) => {
  const navigate = useNavigate();

  return (
    <Flex
      tag="li"
      styles={{ direction: 'column' }}
      css={boxStyling}
      tabIndex={index + 5}
      aria-label={`${index + 1}번째 trip, ${itemName}`}
      onClick={() => navigate(PATH.TRIP(String(id)))}
    >
      <img
        src={coverImage ? convertImageName(coverImage) : DefaultThumbnail}
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
      <Text size="small" css={descriptionStyling}>
        {description}
      </Text>
    </Flex>
  );
};

export default TripsItem;
