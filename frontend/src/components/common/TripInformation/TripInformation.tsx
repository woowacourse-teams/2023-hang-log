import { memo, useState } from 'react';

import { useRecoilValue } from 'recoil';

import { Badge, Box, Flex, Heading, Text, Theme, useOverlay } from 'hang-log-design-system';

import LikeButton from '@components/common/LikeButton/LikeButton';
import TripButtons from '@components/common/TripInformation/TripButtons/TripButtons';
import TripEditButtons from '@components/common/TripInformation/TripEditButtons/TripEditButtons';
import {
  badgeStyling,
  badgeWrapperStyling,
  buttonContainerStyling,
  descriptionStyling,
  imageWrapperStyling,
  sectionStyling,
  titleStyling,
  writerImageStyling,
} from '@components/common/TripInformation/TripInformation.style';
import TripInfoEditModal from '@components/trip/TripInfoEditModal/TripInfoEditModal';

import { useTrip } from '@hooks/trip/useTrip';

import { mediaQueryMobileState } from '@store/mediaQuery';

import convertImageName from '@utils/convertImageName';
import { formatDate } from '@utils/formatter';

import type { TripData, TripTypeData } from '@type/trip';

import { TRIP_TYPE } from '@constants/trip';

import DefaultThumbnail from '@assets/png/trip-information_default-thumbnail.png';

interface TripInformationProps {
  tripType: TripTypeData;
  tripId: string;
  isEditable?: boolean;
  initialTripData?: TripData;
}

const TripInformation = ({
  isEditable = true,
  tripId,
  tripType,
  initialTripData,
}: TripInformationProps) => {
  const isMobile = useRecoilValue(mediaQueryMobileState);
  const isPublished = tripType === TRIP_TYPE.PUBLISHED;
  const { tripData: savedTripData } = useTrip(tripType, tripId);

  const tripData = initialTripData || savedTripData;

  const { isOpen: isEditModalOpen, close: closeEditModal, open: openEditModal } = useOverlay();

  const [likeCount, setLikeCount] = useState(tripData.likeCount);

  const handleLikeCount = (likeCount: number) => {
    setLikeCount(likeCount);
  };

  return (
    <>
      <header css={sectionStyling}>
        <Box css={imageWrapperStyling}>
          <div />
          <img
            src={tripData.imageName ? convertImageName(tripData.imageName) : DefaultThumbnail}
            alt="여행 대표 이미지"
          />
        </Box>
        <Box tag="section">
          <Box css={badgeWrapperStyling}>
            {tripData.cities.map(({ id, name }) => (
              <Badge key={id} css={badgeStyling}>
                {name}
              </Badge>
            ))}
          </Box>
          <Heading css={titleStyling} size={isMobile ? 'medium' : 'large'}>
            {tripData.title}
          </Heading>
          <Text>
            {formatDate(tripData.startDate)} - {formatDate(tripData.endDate)}
          </Text>
          <Text css={descriptionStyling} size="small">
            {tripData.description}
          </Text>
          {isPublished && (
            <Flex
              styles={{
                align: 'center',
                gap: Theme.spacer.spacing4,
                paddingTop: Theme.spacer.spacing3,
              }}
            >
              <Flex styles={{ align: 'center', gap: Theme.spacer.spacing2 }}>
                <img
                  alt="작성자 이미지"
                  src={tripData.writer.imageUrl ?? ''}
                  css={writerImageStyling}
                />
                <Text size="small">{tripData.writer.nickname}</Text>
              </Flex>
              <Flex styles={{ align: 'center', gap: Theme.spacer.spacing2 }}>
                <LikeButton
                  likeCount={likeCount ?? 0}
                  initialState={tripData.isLike ?? false}
                  handleLikeCount={handleLikeCount}
                  tripId={tripId}
                  css={{ height: '20px', cursor: 'pointer' }}
                />
                {likeCount}
              </Flex>
            </Flex>
          )}
        </Box>
        <Box css={buttonContainerStyling}>
          {isEditable ? (
            <TripEditButtons tripId={tripId} openEditModal={openEditModal} />
          ) : (
            <TripButtons
              tripType={tripType}
              tripId={tripId}
              sharedCode={tripData.sharedCode}
              publishState={tripData.isPublished}
            />
          )}
        </Box>
      </header>
      {isEditModalOpen && (
        <TripInfoEditModal isOpen={isEditModalOpen} onClose={closeEditModal} {...tripData} />
      )}
    </>
  );
};

export default memo(TripInformation);
