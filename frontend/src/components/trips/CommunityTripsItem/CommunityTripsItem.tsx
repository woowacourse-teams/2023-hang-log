import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { Badge, Box, Flex, Text } from 'hang-log-design-system';

import LikeButton from '@components/common/LikeButton/LikeButton';
import {
  badgeBoxStyling,
  boxStyling,
  communityItemInfoStyling,
  durationAndDescriptionStyling,
  imageStyling,
  informationStyling,
  likeCountBoxStyling,
  nameStyling,
} from '@components/trips/CommunityTripsItem/CommunityTripsItem.style';

import { formatDate } from '@utils/formatter';

import type { CommunityTripsItemData } from '@type/trips';

import { PATH } from '@constants/path';

import DefaultThumbnail from '@assets/png/trip_default-thumbnail.png';
import EmptyLike from '@assets/svg/empty-like.svg';

interface CommunityTripsItemProps {
  trip: CommunityTripsItemData;
  index: number;
}

const CommunityTripsItem = ({ index, trip }: CommunityTripsItemProps) => {
  const navigate = useNavigate();

  const {
    id,
    imageUrl,
    title,
    cities,
    startDate,
    endDate,
    description,
    authorNickname,
    isLike,
    likeCount: initialLikeCount,
  } = trip;

  const coverImage = imageUrl;
  const duration = `${formatDate(startDate)} - ${formatDate(endDate)}`;

  const [likeCount, setLikeCount] = useState<number>(initialLikeCount);

  const handleLikeCount = (count: number) => {
    setLikeCount(count);
  };

  return (
    <Flex
      tag="li"
      styles={{ direction: 'column', height: '100%' }}
      css={boxStyling}
      tabIndex={index + 5}
      aria-label={`${index + 1}번째 trip, ${title}`}
    >
      <LikeButton
        initialState={isLike}
        tripId={String(id)}
        handleLikeCount={handleLikeCount}
        likeCount={likeCount}
      />
      <img src={coverImage ?? DefaultThumbnail} css={imageStyling} alt={`${title} 대표 이미지`} />
      <Box onClick={() => navigate(PATH.COMMUNITY_TRIP(String(id)))} css={informationStyling}>
        <Box>
          <Box css={badgeBoxStyling}>
            {cities.map((cityTag) => (
              <Badge key={cityTag.id}>{cityTag.name}</Badge>
            ))}
          </Box>
          <Text size="large" css={nameStyling}>
            {title}
          </Text>
          <Text size="medium" css={durationAndDescriptionStyling}>
            {duration}
          </Text>
          <Text size="small" css={durationAndDescriptionStyling}>
            {description}
          </Text>
        </Box>
        <Flex css={communityItemInfoStyling} styles={{ justify: 'space-between' }}>
          <Box>{authorNickname}</Box>
          <Box css={likeCountBoxStyling}>
            <EmptyLike />
            {likeCount}
          </Box>
        </Flex>
      </Box>
    </Flex>
  );
};

export default CommunityTripsItem;
