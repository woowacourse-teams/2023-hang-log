import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { useRecoilValue } from 'recoil';

import { Badge, Box, Flex, Text } from 'hang-log-design-system';

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

import { useLikeMutation } from '@hooks/api/useLikeMutation';

import { isLoggedInState } from '@store/auth';

import { formatDate } from '@utils/formatter';

import type { CommunityTripData } from '@type/trips';

import { PATH } from '@constants/path';

import DefaultThumbnail from '@assets/png/trip_default-thumbnail.png';
import ClickEmptyLike from '@assets/svg/click-empty-like.svg';
import ClickFilledLike from '@assets/svg/click-filled-like.svg';
import EmptyLike from '@assets/svg/empty-like.svg';

interface CommunityTripsItemProps {
  trip: CommunityTripData;
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
    likeCount,
  } = trip;

  const coverImage = imageUrl;
  const duration = `${formatDate(startDate)} - ${formatDate(endDate)}`;

  const likeMutation = useLikeMutation();

  const isLoggedIn = useRecoilValue(isLoggedInState);

  const [isLikeChecked, setisLikeChecked] = useState<boolean>(isLike);

  const handleLikeCheck = (isLike: boolean) => {
    setisLikeChecked(isLike);
    likeMutation.mutate(
      { id, isLike, isLoggedIn },
      {
        onError: () => {
          setisLikeChecked(!isLike);
        },
      }
    );
  };

  return (
    <Flex
      tag="li"
      styles={{ direction: 'column', height: '100%' }}
      css={boxStyling}
      tabIndex={index + 5}
      aria-label={`${index + 1}번째 trip, ${title}`}
    >
      {isLikeChecked ? (
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

      <img src={coverImage ?? DefaultThumbnail} css={imageStyling} alt={`${title} 대표 이미지`} />
      <Box onClick={() => navigate(PATH.TRIP(String(id)))} css={informationStyling}>
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
