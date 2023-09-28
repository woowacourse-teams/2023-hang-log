import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { Badge, Box, Flex, Text, Theme } from 'hang-log-design-system';

import LikeButton from '@components/common/LikeButton/LikeButton';
import { writerImageStyling } from '@components/common/TripInformation/TripInformation.style';
import {
  badgeBoxStyling,
  boxStyling,
  clickableLikeStyling,
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
    writer,
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
        css={clickableLikeStyling}
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
          <Flex styles={{ align: 'center', gap: Theme.spacer.spacing2 }}>
            <img alt="작성자 이미지" src={writer.imageUrl} css={writerImageStyling} />
            <Text size="small">{writer.nickname}</Text>
          </Flex>
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
