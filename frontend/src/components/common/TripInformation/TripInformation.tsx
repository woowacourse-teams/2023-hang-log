import { memo } from 'react';

import { useRecoilValue } from 'recoil';

import { Badge, Box, Heading, Text, useOverlay } from 'hang-log-design-system';

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
} from '@components/common/TripInformation/TripInformation.style';
import TripInfoEditModal from '@components/trip/TripInfoEditModal/TripInfoEditModal';

import { useTrip } from '@hooks/trip/useTrip';

import { mediaQueryMobileState } from '@store/mediaQuery';

import { formatDate } from '@utils/formatter';

import DefaultThumbnail from '@assets/png/trip-information_default-thumbnail.png';

interface TripInformationProps {
  tripId: number;
  isEditable?: boolean;
  isShared?: boolean;
}

const TripInformation = ({ isEditable = true, isShared = false, tripId }: TripInformationProps) => {
  const isMobile = useRecoilValue(mediaQueryMobileState);

  const { isOpen: isEditModalOpen, close: closeEditModal, open: openEditModal } = useOverlay();

  const { tripData } = useTrip(tripId);

  return (
    <>
      <header css={sectionStyling}>
        <Box css={imageWrapperStyling}>
          <div />
          <img src={tripData.imageUrl ?? DefaultThumbnail} alt="여행 대표 이미지" />
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
        </Box>
        <Box css={buttonContainerStyling}>
          {isEditable ? (
            <TripEditButtons tripId={tripId} openEditModal={openEditModal} />
          ) : (
            <TripButtons tripId={tripId} sharedCode={tripData.sharedCode} isShared={isShared} />
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
