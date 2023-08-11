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

import { mediaQueryMobileState } from '@store/mediaQuery';

import { formatDate } from '@utils/formatter';

import type { TripData } from '@type/trip';

import DefaultThumbnail from '@assets/png/trip-information_default-thumbnail.png';

interface TripInformationProps extends Omit<TripData, 'dayLogs'> {
  isEditable?: boolean;
}

const TripInformation = ({ isEditable = true, ...information }: TripInformationProps) => {
  const isMobile = useRecoilValue(mediaQueryMobileState);

  const { isOpen: isEditModalOpen, close: closeEditModal, open: openEditModal } = useOverlay();

  return (
    <>
      <header css={sectionStyling}>
        <Box css={imageWrapperStyling}>
          <div />
          <img src={information.imageUrl ?? DefaultThumbnail} alt="여행 대표 이미지" />
        </Box>
        <Box tag="section">
          <Box css={badgeWrapperStyling}>
            {information.cities.map(({ id, name }) => (
              <Badge key={id} css={badgeStyling}>
                {name}
              </Badge>
            ))}
          </Box>
          <Heading css={titleStyling} size={isMobile ? 'medium' : 'large'}>
            {information.title}
          </Heading>
          <Text>
            {formatDate(information.startDate)} - {formatDate(information.endDate)}
          </Text>
          <Text css={descriptionStyling} size="small">
            {information.description}
          </Text>
        </Box>
        <Box css={buttonContainerStyling}>
          {isEditable ? (
            <TripEditButtons tripId={information.id} openEditModal={openEditModal} />
          ) : (
            <TripButtons tripId={information.id} />
          )}
        </Box>
      </header>
      {isEditModalOpen && (
        <TripInfoEditModal isOpen={isEditModalOpen} onClose={closeEditModal} {...information} />
      )}
    </>
  );
};

export default memo(TripInformation);
