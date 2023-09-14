import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { Badge, Box, Flex, Text } from 'hang-log-design-system';

import {
  badgeBoxStyling,
  boxStyling,
  clickableLikeStyling,
  communityItemInfoStyling,
  durationAndDescriptionStyling,
  imageStyling,
  likeCountBoxStyling,
  nameStyling,
} from '@components/trips/CommunityTripsItem/CommunityTripsItem.style';

import type { CityData } from '@type/city';

import { PATH } from '@constants/path';

import DefaultThumbnail from '@assets/png/trip_default-thumbnail.png';
import ClickEmptyLike from '@assets/svg/click-empty-like.svg';
import ClickFilledLike from '@assets/svg/click-filled-like.svg';
import EmptyLike from '@assets/svg/empty-like.svg';

interface CommunityTripsItemProps {
  id: number;
  coverImage: string | null;
  cityTags: CityData[];
  itemName: string;
  duration: string;
  index: number;
  description?: string | null;
  nickName: string;
  isLikeChecked?: boolean;
  likeCount?: number;
}

const CommunityTripsItem = ({
  id,
  coverImage,
  cityTags,
  itemName,
  duration,
  description,
  index,
  nickName = '',
  isLikeChecked = false,
  likeCount = 0,
}: CommunityTripsItemProps) => {
  const navigate = useNavigate();

  const [likeChecked, setLikeChecked] = useState<boolean>(isLikeChecked);

  const handleLikeCheck = (checkType: boolean) => {
    setLikeChecked(checkType);
  };

  return (
    <Flex
      tag="li"
      styles={{ direction: 'column' }}
      css={boxStyling}
      tabIndex={index + 5}
      aria-label={`${index + 1}번째 trip, ${itemName}`}
    >
      {likeChecked ? (
        <ClickFilledLike
          css={clickableLikeStyling}
          onClick={() => {
            handleLikeCheck(false);
          }}
        />
      ) : (
        <ClickEmptyLike
          css={clickableLikeStyling}
          onClick={() => {
            handleLikeCheck(true);
          }}
        />
      )}
      <img
        src={coverImage ?? DefaultThumbnail}
        css={imageStyling}
        alt={`${itemName} 대표 이미지`}
      />
      <Box onClick={() => navigate(PATH.TRIP(id))}>
        <Box css={badgeBoxStyling}>
          {cityTags.map((cityTag) => {
            return <Badge key={cityTag.id}>{cityTag.name}</Badge>;
          })}
        </Box>
        <Text size="large" css={nameStyling}>
          {itemName}
        </Text>
        <Text size="medium" css={durationAndDescriptionStyling}>
          {duration}
        </Text>
        <Text size="small" css={durationAndDescriptionStyling}>
          {description}
        </Text>
        <Flex css={communityItemInfoStyling} styles={{ justify: 'space-between' }}>
          <Box>{nickName}</Box>
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
